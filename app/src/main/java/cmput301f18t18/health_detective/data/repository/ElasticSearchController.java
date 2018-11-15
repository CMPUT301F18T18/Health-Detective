package cmput301f18t18.health_detective.data.repository;

import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

public class ElasticSearchController implements ProblemRepo, RecordRepo, UserRepo {
    private static final ElasticSearchController ourInstance = new ElasticSearchController();

    static private JestDroidClient client = null;

    public static ElasticSearchController getInstance() {
        setClient();
        return ourInstance;
    }

    private ElasticSearchController() { }

    private static void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080/")
                    .build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    private String getProblemElasticSearchId(Integer problemID) {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"problemId\": " + problemID.toString() + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getProblemElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t18")
                .addType("problem")
                .build();
        try {
            SearchResult result = client.execute(search);
            List<Hit<Problem, Void>> problems = result.getHits(Problem.class);

            Log.d("ESC:getProblemElasticSearchId", "Result succeeded");

            if (problems.size() == 0) {
                Log.d("ESC:getProblemElasticSearchId", "No results found");
                return null;
            }
            for (Hit<Problem, Void> hit : problems) {
                Problem prob = hit.source;
                Log.d("ESC:getProblemElasticSearchId", hit.id);
            }
            return problems.get(0).id;
        } catch (IOException e) { }

        return null;
    }

    @Override
    public void insertProblem(Problem problem) {
        Index index = new Index.Builder(problem)
                .index("cmput301f18t18")
                .type("problem")
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

    @Override
    public void updateProblem(Problem problem) {

    }

    @Override
    public Problem retrieveProblemById(Integer problemId) {
        String elasticSearchId = getProblemElasticSearchId(problemId);
        Get get = new Get.Builder("cmput301f18t18", elasticSearchId)
                .type("problem")
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

    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<Integer> problemId) {
        ArrayList<Problem> problems = new ArrayList<>();

        for (Integer id : problemId) {
            problems.add(retrieveProblemById(id));
        }

        return problems;
    }

    @Override
    public void deleteProblem(Problem problem) {
        String elasticSearchId = getProblemElasticSearchId(problem.getProblemID());
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index("cmput301f18t18")
                .type("problem")
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

    private String getRecordElasticSearchId(Integer recordID) {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"recordId\": " + recordID.toString() + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getRecordElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t18")
                .addType("record")
                .build();
        try {
            SearchResult result = client.execute(search);
            List<Hit<Record, Void>> records = result.getHits(Record.class);

            Log.d("ESC:getRecordElasticSearchId", "Result succeeded");

            if (records.size() == 0) {
                Log.d("ESC:getRecordElasticSearchId", "No results found");
                return null;
            }
            for (Hit<Record, Void> hit : records) {
                Record rec = hit.source;
                Log.d("ESC:getRecordElasticSearchId", hit.id);
            }
            return records.get(0).id;
        } catch (IOException e) {
            Log.d("ESC:getRecordElasticSearchId", "IOEception", e);
        }

        return null;
    }

    @Override
    public void insertRecord(Record record) {
        Index index = new Index.Builder(record)
                .index("cmput301f18t18")
                .type(record.getClass().getSimpleName().toLowerCase())
                .refresh(true)
                .build();
        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                Log.d("ESC:insertRecord", "Record inserted");
                Log.d("ESC:insertRecord", result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:insertRecord", "IOException", e);
        }
    }

    @Override
    public void updateRecord(Record record) {

    }

    @Override
    public Record retrieveRecordById(Integer recordID) {
        String elasticSearchId = getRecordElasticSearchId(recordID);
        Get get = new Get.Builder("cmput301f18t18", elasticSearchId)
                .type("record")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                return result.getSourceAsObject(Record.class);
            }
            else {
                Log.d("ESC:retrieveRecordById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveRecordById", "IOException", e);
        }
        return null;
    }

    @Override
    public ArrayList<Record> retrieveRecordsById(ArrayList<Integer> recordID) {
        ArrayList<Record> records = new ArrayList<>();

        for (Integer id : recordID) {
            records.add(retrieveRecordById(id));
        }

        return records;
    }

    @Override
    public void deleteRecord(Record record) {
        String elasticSearchId = getRecordElasticSearchId(record.getRecordId());
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index("cmput301f18t18")
                .type(record.getClass().getSimpleName().toLowerCase())
                .build();
        try {
            DocumentResult result = client.execute(delete);
            if (result.isSucceeded()) {
                Log.d("ESC:deleteRecord", "Deleted" + result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:deleteRecord", "IOException", e);
        }
    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public Patient retrievePatientById(String patientId) {
        return null;
    }

    @Override
    public ArrayList<Patient> retrievePatientsById(ArrayList<String> patientIds) {
        return null;
    }

    @Override
    public CareProvider retrieveCareProviderById(String careProviderId) {
        return null;
    }

    @Override
    public boolean validateUserIdUniqueness(String userId) {
        return false;
    }
}
