package cmput301f18t18.health_detective.data.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class ElasticSearchController implements ProblemRepo, RecordRepo, UserRepo {
    private static final ElasticSearchController ourInstance = new ElasticSearchController();

    public static ElasticSearchController getInstance() {
        return ourInstance;
    }

    private ElasticSearchController() {
    }

    @Override
    public void insertProblem(Problem problem) {

    }

    @Override
    public void updateProblem(Problem problem) {

    }

    @Override
    public Problem retrieveProblemById(Integer problemID) {
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
    public User retrieveUserById(String userID) {
        return null;
    }

    @Override
    public ArrayList<User> retrieveUsersById(ArrayList<String> userID) {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }
}
