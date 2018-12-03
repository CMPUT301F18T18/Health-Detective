package cmput301f18t18.health_detective.data.transaction;

import android.content.ContentValues;
import android.database.Cursor;
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

    public UserRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, Boolean online, User user) {
        super(client, index, db, online);
        this.user = user;
        this.userId = user.getUserId();
    }

    public UserRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, Boolean online, String id) {
        super(client, index, db, online);
        this.userId = id;
    }

    /**
     * Returns a users's unique _ID out of elastic search based on a user ID.
     * Used in deletion.
     *
     * @return          The user's _ID from elasticsearch
     */
    private String getUserElasticSearchId() {
        if (!online) {
            return null;
        }
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"userId\": " + "\"" + userId +  "\"" + "\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getUserElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex(elasticIndex)
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
            Log.d("ESC:getUserElasticSearchId", "IOException", e);
        }
        return null;
    }

    /**
     * Function to push a user into elastic search.
     *
     */
    @Override
    public void insert() {
        if (online) {
            Index index = new Index.Builder(user)
                    .index(elasticIndex)
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
        insertLocal();
    }

    private void insertLocal() {
        if (user instanceof Patient)
            insertLocalPatient();
        else if (user instanceof  CareProvider)
            insertLocalCareProvider();
    }

    private void insertLocalPatient() {
        Patient pat = (Patient) user;
        ContentValues values = new ContentValues();
        values.put("userId", pat.getUserId());
        values.put("phone", pat.getPhoneNumber());
        values.put("email", pat.getEmailAddress());

        StringBuilder stringBuilder = new StringBuilder();
        for (String id : pat.getProblemIds()) {
            stringBuilder.append(id).append(",");
        }

        String subIds = stringBuilder.toString();
        if (stringBuilder.length() > 2) { // Trim trailing ","
            subIds = subIds.substring(0, subIds.length()-1);
        }

        values.put("problemIds", subIds);
        db.insert("Patients", null, values);
    }

    private void insertLocalCareProvider() {
        CareProvider pat = (CareProvider) user;
        ContentValues values = new ContentValues();
        values.put("userId", pat.getUserId());
        values.put("phone", pat.getPhoneNumber());
        values.put("email", pat.getEmailAddress());

        StringBuilder stringBuilder = new StringBuilder();
        for (String id : pat.getPatientIds()) {
            stringBuilder.append(id).append(",");
        }

        String subIds = stringBuilder.toString();
        if (stringBuilder.length() > 2) { // Trim trailing ","
            subIds = subIds.substring(0, subIds.length()-1);
        }

        values.put("patientIds", subIds);
        db.insert("CareProviders", null, values);
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
        if (elasticSearchId == null) {
            deleteLocal();
            return;
        }
        Delete delete = new Delete.Builder(elasticSearchId)
                .index(elasticIndex)
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
        deleteLocal();
    }

    private void deleteLocal() {
        String table;
        if (user instanceof  Patient)
            table = "Patients";
        else if (user instanceof CareProvider)
            table = "CareProviders";
        else
            return;

        db.delete(table,
                "userId = ?",
                new String[] {userId}
        );
    }

    /**
     * Used to return a patient from elasticsearch based on its patientId
     *
     * @return          The patient associated with patientId
     */
    public Patient retrievePatient() {
        Log.d("ESC:getPatientElasticSearchId", userId);
        String elasticSearchId = getUserElasticSearchId();
        if (elasticSearchId == null) {
            Log.d("ESC:retrievePatientById", "Patient not found in Elastic");
            return retrieveLocalPatient();
        }
        Get get = new Get.Builder(elasticIndex, elasticSearchId)
                .type("Patient")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                user = result.getSourceAsObject(Patient.class);
            }
            else {
                Log.d("ESC:retrievePatientById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrievePatientById", "IOException", e);
            return retrieveLocalPatient();
        }

        if (retrieveLocalPatient() == null) {
            insertLocalPatient();
        }

        return (Patient) user;
    }

    private Patient retrieveLocalPatient() {
        Cursor cursor = db.query(
                "Patients",
                null,
                "userId = ?",
                new String[] {userId},
                null,
                null,
                null);
        Patient ret = null;
        if(cursor.moveToNext()) {
            String userId = cursor.getString(0);
            String phone = cursor.getString(1);
            String email = cursor.getString(2);


            ret = new Patient(userId, phone, email);

            for (String recordId : cursor.getString(3).split(",")) {
                ret.addProblem(recordId);
            }
        }

        cursor.close();
        return ret;
    }

    /**
     * Used to return a CareProvider from elasticsearch based on its careProviderId
     *
     * @return          The careprovider associated with careproviderId
     */
    public CareProvider retrieveCareProvider() {
        Log.d("ESC:getCareProviderElasticSearchId", userId);
        String elasticSearchId = getUserElasticSearchId();
        if (elasticSearchId == null)
            return retrieveLocalCareProvider();
        Get get = new Get.Builder(elasticIndex, elasticSearchId)
                .type("CareProvider")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                user = result.getSourceAsObject(CareProvider.class);
            }
            else {
                Log.d("ESC:retrieveCareProviderById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveCareProviderById", "IOException", e);
            return retrieveLocalCareProvider();
        }

        if (retrieveLocalCareProvider() == null) {
            insertLocalCareProvider();
        }
        return (CareProvider) user;
    }


    private CareProvider retrieveLocalCareProvider() {
        Cursor cursor = db.query("CareProviders", null, "userId = ?", new String[] {userId}, null, null, null);
        CareProvider ret = null;
        if(cursor.moveToNext()) {
            String userId = cursor.getString(0);
            String phone = cursor.getString(1);
            String email = cursor.getString(2);

            ret = new CareProvider(userId, phone, email);

            for (String patientId : cursor.getString(3).split(",")) {
                ret.addPatient(patientId);
            }
        }
        cursor.close();
        return ret;
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
