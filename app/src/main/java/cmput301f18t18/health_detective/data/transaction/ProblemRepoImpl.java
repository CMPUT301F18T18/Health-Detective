package cmput301f18t18.health_detective.data.transaction;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
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

    public ProblemRepoImpl(JestDroidClient client, SQLiteDatabase db, Problem problem) {
        super(client, db);
        this.problem = problem;
        this.problemId = problem.getProblemId();
    }

    public ProblemRepoImpl(JestDroidClient client, SQLiteDatabase db, String id) {
        super(client, db);
        this.problemId = id;
    }

    /**
     * Returns a problem's unique _ID out of elastic search based on a problem ID.
     * Used in deletion.
     *
     * @return          The problem's _ID from elasticsearch
     */
    private String getProblemElasticSearchId() {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"problemId\": \"" + problemId + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getProblemElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t18test2")
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
    public void insert() {
        if (problem == null)
            return;
        Index index = new Index.Builder(problem)
                .index("cmput301f18t18test2")
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

    /**
     * Used to update the old info for a problem in elasticsearch with new info.
     * Currently done by deleting then re-inserting problem.
     *
     */
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
        Get get = new Get.Builder("cmput301f18t18test2", elasticSearchId)
                .type("Problem")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                return result.getSourceAsObject(Problem.class);
            }
            else {
                Log.d("ESC:retrieveProblemById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveProblemById", "IOException", e);
        }
        return null;
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
    public void delete() {
        String elasticSearchId = getProblemElasticSearchId();
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index("cmput301f18t18test2")
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
    }
}
