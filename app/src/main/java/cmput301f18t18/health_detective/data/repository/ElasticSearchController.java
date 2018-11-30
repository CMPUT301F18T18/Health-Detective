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

/**
 * Singleton that holds the information and methods for interfacing with elastic search
 */
public class ElasticSearchController implements ProblemRepo, RecordRepo, UserRepo {
    private static final ElasticSearchController ourInstance = new ElasticSearchController();

    static private JestDroidClient client = null;

    /**
     * Allows other classes to get a reference to the singleton
     *
     * @return reference to this object
     */
    public static synchronized ElasticSearchController getInstance() {
        setClient();
        return ourInstance;
    }

    private ElasticSearchController() {
    }

    /**
     * Runs whenever another class gets a reference to this object.
     * Sets the JestDroidClient parameters and info
     */
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

    /**
     * Returns a problem's unique _ID out of elastic search based on a problem ID.
     * Used in deletion.
     *
     * @param problemId A unique integer to a specific problem that we can to get
     * @return          The problem's _ID from elasticsearch
     */
    private String getProblemElasticSearchId(String problemId) {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"problemId\": " + problemId + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getProblemElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t18test")
                .addType("Problem")
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
     * @param problem The problem to add to elastic search
     */
    @Override
    public void insertProblem(Problem problem) {
        Index index = new Index.Builder(problem)
                .index("cmput301f18t18test")
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
     * @param problem The problem to update
     */
    @Override
    public void updateProblem(Problem problem) {
        deleteProblem(problem);
        insertProblem(problem);
    }

    /**
     * Used to return a problem from elasticsearch based on its problemId
     *
     * @param problemId The problems designated ID
     * @return          The problem associated with problemId
     */
    @Override
    public Problem retrieveProblemById(String problemId) {
        String elasticSearchId = getProblemElasticSearchId(problemId);
        Get get = new Get.Builder("cmput301f18t18test", elasticSearchId)
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
    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<String> problemId) {
        ArrayList<Problem> problems = new ArrayList<>();

        for (String id : problemId) {
            problems.add(retrieveProblemById(id));
        }

        return problems;
    }

    /**
     * Used to remove a problem from elasticsearch
     *
     * @param problem The problem object to remove
     */
    @Override
    public void deleteProblem(Problem problem) {
        String elasticSearchId = getProblemElasticSearchId(problem.getProblemID());
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index("cmput301f18t18test")
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

    /**
     * Returns a records's unique _ID out of elastic search based on a record ID.
     * Used in deletion.
     *
     * @param recordID A unique integer to a specific problem that we can to get
     * @return          The record's _ID from elasticsearch
     */
    private String getRecordElasticSearchId(String recordID) {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"recordId\": " + recordID + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getRecordElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t18test")
                .addType("Record")
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
                Log.d("ESC:getRecordElasticSearchId", hit.id);
            }
            return records.get(0).id;
        } catch (IOException e) {
            Log.d("ESC:getRecordElasticSearchId", "IOEception", e);
        }

        return null;
    }

    /**
     * Function to push a record into elastic search.
     *
     * @param record The record to add to elastic search
     */
    @Override
    public void insertRecord(Record record) {
        Index index = new Index.Builder(record)
                .index("cmput301f18t18test")
                .type(record.getClass().getSimpleName())
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

    /**
     * Used to update the old info for a record in elasticsearch with new info.
     * Currently done by deleting then re-inserting record.
     *
     * @param record The record to update
     */
    @Override
    public void updateRecord(Record record) {
        deleteRecord(record);
        insertRecord(record);
    }

    /**
     * Used to return a record from elasticsearch based on its recordID
     *
     * @param recordID The record's designated ID
     * @return          The record associated with recordID
     */
    @Override
    public Record retrieveRecordById(String recordID) {
        String elasticSearchId = getRecordElasticSearchId(recordID);
        Get get = new Get.Builder("cmput301f18t18test", elasticSearchId)
                .type("Record")
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

    /**
     * Gets a list of records from a list of record IDs
     *
     * @param recordID The list of record ids to find
     * @return          A list of associated record ids
     */
    @Override
    public ArrayList<Record> retrieveRecordsById(ArrayList<String> recordID) {
        ArrayList<Record> records = new ArrayList<>();

        for (String id : recordID) {
            records.add(retrieveRecordById(id));
        }

        return records;
    }

    /**
     * Used to remove a record from elasticsearch
     *
     * @param record The record object to remove
     */
    @Override
    public void deleteRecord(Record record) {
        String elasticSearchId = getRecordElasticSearchId(record.getRecordId());
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index("cmput301f18t18test")
                .type(record.getClass().getSimpleName())
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


    /**
     * Returns a users's unique _ID out of elastic search based on a user ID.
     * Used in deletion.
     *
     * @param userId A unique integer to a specific user that we can to get
     * @return          The user's _ID from elasticsearch
     */
    private String getUserElasticSearchId(String userId) {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"userId\": " + "\"" + userId +  "\"" + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getUserElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t18test")
                .addType("Patient")
                .addType("CareProvider")
                .build();
        try {
            SearchResult result = client.execute(search);
            List<Hit<User, Void>> users = result.getHits(User.class);

            Log.d("ESC:getUserElasticSearchId", "User succeeded");

            if (users.size() == 0) {
                Log.d("ESC:getUserElasticSearchId", "No results found");
                return null;
            }
            for (Hit<User, Void> hit : users) {
                Log.d("ESC:getUserElasticSearchId", hit.id);
            }
            return users.get(0).id;
        } catch (IOException e) {
            Log.d("ESC:getUserElasticSearchId", "IOEception", e);
        }
        return null;
    }

    /**
     * Function to push a user into elastic search.
     *
     * @param user The user to add to elastic search
     */
    @Override
    public void insertUser(User user) {
        Index index = new Index.Builder(user)
                .index("cmput301f18t18test")
                .type(user.getClass().getSimpleName())
                .refresh(true)
                .build();
        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                Log.d("ESC:insertUser", "User inserted");
                Log.d("ESC:insertUser", result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:insertUser", "IOException", e);
        }
    }

    /**
     * Used to update the old info for a user in elasticsearch with new info.
     * Currently done by deleting then re-inserting user.
     *
     * @param user The user to update
     */
    @Override
    public void updateUser(User user) {
        deleteUser(user);
        insertUser(user);
    }

    /**
     * Used to remove a user from elasticsearch
     *
     * @param user The user object to remove
     */
    @Override
    public void deleteUser(User user) {
        String elasticSearchId = getUserElasticSearchId(user.getUserId());
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index("cmput301f18t18test")
                .type(user.getClass().getSimpleName())
                .build();
        try {
            DocumentResult result = client.execute(delete);
            if (result.isSucceeded()) {
                Log.d("ESC:deleteUser", "Deleted" + result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:deleteUser", "IOException", e);
        }
    }

    /**
     * Used to return a patient from elasticsearch based on its patientId
     *
     * @param patientId The patient's designated ID
     * @return          The patient associated with patientId
     */
    @Override
    public Patient retrievePatientById(String patientId) {
        String elasticSearchId = getUserElasticSearchId(patientId);
        Get get = new Get.Builder("cmput301f18t18test", elasticSearchId)
                .type("Patient")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                return result.getSourceAsObject(Patient.class);
            }
            else {
                Log.d("ESC:retrieveRecordById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveRecordById", "IOException", e);
        }
        return null;
    }

    /**
     * Gets a list of patients from a list of patient IDs
     *
     * @param patientIds The list of patient ids to find
     * @return          A list of associated patient ids
     */
    @Override
    public ArrayList<Patient> retrievePatientsById(ArrayList<String> patientIds) {
        ArrayList<Patient> patients = new ArrayList<>();

        for (String id : patientIds) {
            patients.add(retrievePatientById(id));
        }

        return patients;
    }

    /**
     * Used to return a CareProvider from elasticsearch based on its careProviderId
     *
     * @param careProviderId The careprovider's designated ID
     * @return          The careprovider associated with careproviderId
     */
    @Override
    public CareProvider retrieveCareProviderById(String careProviderId) {
        String elasticSearchId = getUserElasticSearchId(careProviderId);
        Get get = new Get.Builder("cmput301f18t18test", elasticSearchId)
                .type("CareProvider")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                return result.getSourceAsObject(CareProvider.class);
            }
            else {
                Log.d("ESC:retrieveRecordById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveRecordById", "IOException", e);
        }
        return null;
    }

    /**
     * Checks whether a usedId is unique within elasticsearch
     *
     * @param userId userId to check uniqueness
     * @return True if userId does not exist in elastic search
     */
    @Override
    public boolean validateUserIdUniqueness(String userId) {
        return getUserElasticSearchId(userId) == null;
    }
}
