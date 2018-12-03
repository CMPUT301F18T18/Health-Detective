package cmput301f18t18.health_detective.data.transaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmput301f18t18.health_detective.data.transaction.base.AbstractRepo;
import cmput301f18t18.health_detective.domain.model.Problem;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ProblemRepoImpl extends AbstractRepo {

    private Problem problem;
    private String problemId;

    public ProblemRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, Boolean online, Problem problem) {
        super(client, index, db, online);
        this.problem = problem;
        this.problemId = problem.getProblemId();
    }

    public ProblemRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, Boolean online, String id) {
        super(client, index, db, online);
        this.problemId = id;
    }

    /**
     * Returns a problem's unique _ID out of elastic search based on a problem ID.
     * Used in deletion.
     *
     * @return          The problem's _ID from elasticsearch
     */
    private String getProblemElasticSearchId() {
        if (!online)
            return null;
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"problemId\": \"" + problemId + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getProblemElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex(elasticIndex)
                .addType("Problem")
                .build();
        try {
            SearchResult result = client.execute(search);
            List<SearchResult.Hit<Problem, Void>> problems = result.getHits(Problem.class);

            Log.d("ESC:getProblemElasticSearchId", "Result succeeded");

            if (problems.size() == 0) {
                Log.d("ESC:getProblemElasticSearchId", "No results found");
                return null;
            }
            for (SearchResult.Hit<Problem, Void> hit : problems) {
                Log.d("ESC:getProblemElasticSearchId", hit.id);
            }
            return problems.get(0).id;
        } catch (IOException e) {
            Log.d("ESC:getProblemElasticSearchId", "IOException", e);
        }
        return null;
    }

    /**
     * Function to push a problem into elastic search.
     *
     */
    @Override
    public void insert() {
        if (online)
        {
            if (problem == null)
                return;
            Index index = new Index.Builder(problem)
                    .index(elasticIndex)
                    .type(problem.getClass().getSimpleName())
                    .refresh(true)
                    .build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    Log.d("ESC:insertProblem", "Problem inserted");
                    Log.d("ESC:insertProblem", result.getId());
                }
            } catch (IOException e) {
                Log.d("ESC:insertProblem", "IOException", e);
            }
        }
        insertLocal();
    }

    private void insertLocal() {
        ContentValues values = new ContentValues();
        values.put("problemId", problem.getProblemId());
        values.put("title", problem.getTitle());
        values.put("description", problem.getDescription());
        values.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problem.getStartDate()));

        StringBuilder stringBuilder = new StringBuilder();
        for (String id : problem.getRecordIds()) {
            stringBuilder.append(id).append(",");
        }

        String recordIds = stringBuilder.toString();
        if (stringBuilder.length() > 2) { // Trim trailing ","
            recordIds = recordIds.substring(0, recordIds.length()-1);
        }

        values.put("recordIds", recordIds);

        db.insert("Problems", null, values);
    }

    /**
     * Used to update the old info for a problem in elasticsearch with new info.
     * Currently done by deleting then re-inserting problem.
     *
     */
    @Override
    public void update() {
        delete();
        insert();
    }

    /**
     * Used to return a problem from elasticsearch based on its problemId
     *
     * @return          The problem associated with problemId
     */
    public Problem retrieve() {
        String elasticSearchId = getProblemElasticSearchId();
        if (elasticSearchId == null) {
            return retrieveLocal();
        }
        Get get = new Get.Builder(elasticIndex, elasticSearchId)
                .type("Problem")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                problem = result.getSourceAsObject(Problem.class);
            }
            else {
                Log.d("ESC:retrieveProblemById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveProblemById", "IOException", e);
            return retrieveLocal();
        }

        if (retrieveLocal() == null) {
            insertLocal();
        }

        return problem;
    }

    private Problem retrieveLocal() {
        Cursor cursor = db.query(
                "Problems",
                null,
                "problemId = ?",
                new String[] {problemId},
                null,
                null,
                null);
        Problem ret = null;
        if(cursor.moveToNext()) {
            try {
                String problemId = cursor.getString(0);
                String problemTitle = cursor.getString(1);
                String problemDesc = cursor.getString(2);
                Date problemDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(3));

                ret = new Problem(problemId, problemTitle, problemDesc, problemDate);

                for (String recordId : cursor.getString(4).split(",")) {
                    ret.addRecord(recordId);
                }
            } catch (ParseException e) {
                Log.d("DBC:retrieveProblemById", "Parse exception", e);
                ret = null;
            }
        }
        cursor.close();
        return ret;
    }
    /**
     * Gets a list of problems from a list of problem IDs
     *
     * @param problemId The list of problem ids to find
     * @return          A list of associated problem ids
     */
    public ArrayList<Problem> retrieveProblemsById(ArrayList<String> problemId) {
        ArrayList<Problem> problems = new ArrayList<>();

        for (String id : problemId) {
            this.problemId = id;
            problems.add(retrieve());
        }

        return problems;
    }

    /**
     * Used to remove a problem from elasticsearch
     *
     */
    @Override
    public void delete() {
        String elasticSearchId = getProblemElasticSearchId();
        if (elasticSearchId == null) {
            deleteLocal();
            return;
        }
        Delete delete = new Delete.Builder(elasticSearchId)
                .index(elasticIndex)
                .type(problem.getClass().getSimpleName())
                .build();
        try {
            DocumentResult result = client.execute(delete);
            if (result.isSucceeded()) {
                Log.d("ESC:deleteProblem", "Deleted" + result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:deleteProblem", "IOException", e);
        }
        deleteLocal();
    }

    private void deleteLocal() {
        db.delete("Problems",
                "problemId = ?",
                new String[] {problemId}
        );
    }
}
