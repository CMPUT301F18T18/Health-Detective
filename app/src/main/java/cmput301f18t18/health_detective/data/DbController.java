package cmput301f18t18.health_detective.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;

import cmput301f18t18.health_detective.data.transaction.RecordRepoImpl;
import cmput301f18t18.health_detective.data.transaction.UserRepoImpl;
import cmput301f18t18.health_detective.data.transaction.base.AbstractRepo;
import cmput301f18t18.health_detective.data.transaction.SQL.LocalDbHelper;
import cmput301f18t18.health_detective.data.transaction.ProblemRepoImpl;
import cmput301f18t18.health_detective.data.transaction.factory.RepoFactory;
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

    public void init_ONLY_CALL_START(Context context) {
        db = new LocalDbHelper(context).getWritableDatabase();
    }

    public void closeDB_ONLY_CALL_END() {
        db.close();
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
    }

    @Override
    public void updateProblem(Problem problem) {
        AbstractRepo repo = RepoFactory.build(client, db, problem);
        repo.update();
    }

    @Override
    public Problem retrieveProblemById(String problemId) {
        AbstractRepo repo = RepoFactory.build(client, db, Problem.class, problemId);
        if (repo instanceof ProblemRepoImpl) {
            return ((ProblemRepoImpl) repo).retrieve();
        }
        return null;
    }

    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<String> problemId) {
        AbstractRepo repo = RepoFactory.build(client, db, Problem.class, null);
        if (repo instanceof ProblemRepoImpl) {
            return ((ProblemRepoImpl) repo).retrieveProblemsById(problemId);
        }
        return null;
    }

    @Override
    public void deleteProblem(Problem problem) {
        AbstractRepo repo = RepoFactory.build(client, db, problem);
        repo.delete();
    }

    @Override
    public void insertRecord(Record record) {
        AbstractRepo repo = RepoFactory.build(client, db, record);
        repo.insert();
    }

    @Override
    public void updateRecord(Record record) {
        AbstractRepo repo = RepoFactory.build(client, db, record);
        repo.update();
    }

    @Override
    public Record retrieveRecordById(String recordId) {
        AbstractRepo repo = RepoFactory.build(client, db, Record.class, recordId);
        if (repo instanceof RecordRepoImpl) {
            return ((RecordRepoImpl) repo).retrieve();
        }
        return null;
    }

    @Override
    public ArrayList<Record> retrieveRecordsById(ArrayList<String> recordId) {
        AbstractRepo repo = RepoFactory.build(client, db, Record.class, null);
        if (repo instanceof RecordRepoImpl) {
            return ((RecordRepoImpl) repo).retrieveRecordsById(recordId);
        }
        return null;
    }

    @Override
    public void deleteRecord(Record record) {
        AbstractRepo repo = RepoFactory.build(client, db, record);
        repo.delete();
    }

    @Override
    public void insertUser(User user) {
        AbstractRepo repo = RepoFactory.build(client, db, user);
        repo.insert();
    }

    @Override
    public void updateUser(User user) {
        AbstractRepo repo = RepoFactory.build(client, db, user);
        repo.update();
    }

    @Override
    public void deleteUser(User user) {
        AbstractRepo repo = RepoFactory.build(client, db, user);
        repo.delete();
    }

    @Override
    public Patient retrievePatientById(String patientId) {
        AbstractRepo repo = RepoFactory.build(client, db, Patient.class, patientId);
        if (repo instanceof UserRepoImpl) {
            User usr = ((UserRepoImpl) repo).retrievePatient();
            if (usr != null)
                return (Patient) usr;
        }
        return null;
    }

    @Override
    public ArrayList<Patient> retrievePatientsById(ArrayList<String> patientIds) {
        AbstractRepo repo = RepoFactory.build(client, db, Patient.class, null);
        if (repo instanceof UserRepoImpl) {
            return ((UserRepoImpl) repo).retrievePatientsById(patientIds);
        }

        return null;
    }

    @Override
    public CareProvider retrieveCareProviderById(String careProviderId) {
        AbstractRepo repo = RepoFactory.build(client, db, CareProvider.class, careProviderId);
        if (repo instanceof UserRepoImpl) {
            User usr = ((UserRepoImpl) repo).retrieveCareProvider();
            if (usr != null)
                return (CareProvider) usr;
        }
        return null;
    }

    @Override
    public boolean validateUserIdUniqueness(String userId) {
        AbstractRepo repo = RepoFactory.build(client, db, User.class, userId);
        if (repo instanceof UserRepoImpl)
            return ((UserRepoImpl) repo).validateUserIdUniqueness();
        return false;
    }
}
