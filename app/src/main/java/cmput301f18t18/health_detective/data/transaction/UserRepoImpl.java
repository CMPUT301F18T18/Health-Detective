package cmput301f18t18.health_detective.data.transaction;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.data.transaction.base.AbstractRepo;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class UserRepoImpl extends AbstractRepo {

    private User user;
    private String userId;

    public UserRepoImpl(JestDroidClient client, SQLiteDatabase db, User user) {
        super(client, db);
        this.user = user;
        this.userId = user.getUserId();
    }

    public UserRepoImpl(JestDroidClient client, SQLiteDatabase db, String id) {
        super(client, db);
        this.userId = id;
    }

    /**
     * Returns a users's unique _ID out of elastic search based on a user ID.
     * Used in deletion.
     *
     * @return          The user's _ID from elasticsearch
     */
    private String getUserElasticSearchId() {
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
            List<SearchResult.Hit<User, Void>> users = result.getHits(User.class);

            Log.d("ESC:getUserElasticSearchId", "User succeeded");

            if (users.size() == 0) {
                Log.d("ESC:getUserElasticSearchId", "No results found");
                return null;
            }
            for (SearchResult.Hit<User, Void> hit : users) {
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
     */
    @Override
    public void insert() {
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
     */
    @Override
    public void update() {
        delete();
        insert();
    }

    /**
     * Used to remove a user from elasticsearch
     *
     */
    @Override
    public void delete() {
        String elasticSearchId = getUserElasticSearchId();
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index("cmput301f18t18test")
                .type(user.getClass().getSimpleName())
                .refresh(true)
                .build();
        Log.d("ESC:deleteUser", user.getClass().getSimpleName());
        try {
            DocumentResult result = client.execute(delete);
            if (result.isSucceeded()) {
                Log.d("ESC:deleteUser", "Deleted " + result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:deleteUser", "IOException", e);
        }
    }

    /**
     * Used to return a patient from elasticsearch based on its patientId
     *
     * @return          The patient associated with patientId
     */
    public Patient retrievePatient() {
        String elasticSearchId = getUserElasticSearchId();
        Get get = new Get.Builder("cmput301f18t18test", elasticSearchId)
                .type("Patient")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                return result.getSourceAsObject(Patient.class);
            }
            else {
                Log.d("ESC:retrieveUserById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveUserById", "IOException", e);
        }
        return null;
    }


    /**
     * Used to return a CareProvider from elasticsearch based on its careProviderId
     *
     * @return          The careprovider associated with careproviderId
     */
    public CareProvider retrieveCareProvider() {
        String elasticSearchId = getUserElasticSearchId();
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
     * Gets a list of patients from a list of patient IDs
     *
     * @param patientIds The list of patient ids to find
     * @return          A list of associated patient ids
     */
    public ArrayList<Patient> retrievePatientsById(ArrayList<String> patientIds) {
        ArrayList<Patient> patients = new ArrayList<>();

        for (String id : patientIds) {
            userId = id;
            User pat = retrievePatient();
            if (pat != null)
                patients.add((Patient) pat);
        }
        return patients;
    }

    /**
     * Checks whether a usedId is unique within elasticsearch
     *
     * @return True if userId does not exist in elastic search
     */
    public boolean validateUserIdUniqueness() {
        return getUserElasticSearchId() == null;
    }
}
