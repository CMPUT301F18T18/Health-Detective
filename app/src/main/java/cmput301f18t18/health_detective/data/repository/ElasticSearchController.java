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
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

public class ElasticSearchController implements ProblemRepo, RecordRepo, UserRepo {
    private static final ElasticSearchController ourInstance = new ElasticSearchController();

    static JestDroidClient client = null;

    public static ElasticSearchController getInstance() {
        return ourInstance;
    }

    private ElasticSearchController() { }

    public static void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080/")
                    .build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    @Override
    public void insertProblem(Problem problem) {
        setClient();
        Index index = new Index.Builder(problem)
                .index("cmput301f18t18")
                .type("problem")
                .build();
        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                problem.setProblemJestId(result.getId());
            }
        } catch (IOException e) {

        }
    }

    @Override
    public void updateProblem(Problem problem) {

    }

    @Override
    public Problem retrieveProblemById(Integer problemID) {
        setClient();
        String query= "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"problemID\": " + problemID.toString() + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t18")
                .addType("problem")
                .build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Hit<Problem, Void>> problems = result.getHits(Problem.class);

                Log.d("retrieveProblemByID", "Result succeeded");

                if (problems.size() == 0) {
                    Log.d("retrieveProblemByID", "No results found");
                    return null;
                }
                for (Hit<Problem, Void> hit : problems) {
                    Problem prob = hit.source;
                    Log.d("retrieveProblemByID", prob.getDescription());
                }
            }
            else {
                Log.d("retrieveProblemByID", "result not succeeded");
            }


        } catch (IOException e) {
            Log.d("retrieveProblemByID", "IOException");
        }
        return null;
    }

    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<Integer> problemID) {
        return null;
    }

    @Override
    public void deleteProblem(Problem problem) {

    }

    @Override
    public void insertRecord(Record record) {

    }

    @Override
    public void updateRecord(Record record) {

    }

    @Override
    public Record retrieveRecordById(Integer recordID) {
        return null;
    }

    @Override
    public ArrayList<Record> retrieveRecordsById(ArrayList<Integer> recordID) {
        return null;
    }

    @Override
    public void deleteRecord(Record record) {

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
