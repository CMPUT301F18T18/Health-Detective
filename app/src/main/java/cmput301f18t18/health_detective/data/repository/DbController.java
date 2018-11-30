package cmput301f18t18.health_detective.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class DbController implements UserRepo, ProblemRepo, RecordRepo {


    private static final DbController ourInstance = new DbController();

    static private JestDroidClient client = null;
    static private SQLiteDatabase db = null;

    /**
     * Allows other classes to get a reference to the singleton
     *
     * @return reference to this object
     */
    public static DbController getInstance() {
        setClient();
        return ourInstance;
    }

    private DbController() {
    }

    public void init(Context context) {
        db = new LocalDbHelper(context).getWritableDatabase();
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

    @Override
    public void insertProblem(Problem problem) {
        AbstractRepo repo = RepoFactory.build(client, db, problem);
        repo.insert();
//        ProblemRepoImpl.insertProblem(client, problem);
    }

    @Override
    public void updateProblem(Problem problem) {
    }

    @Override
    public Problem retrieveProblemById(Integer problemID) {
        AbstractRepo repo = RepoFactory.build(client, db, Problem.class, problemID.toString());
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
