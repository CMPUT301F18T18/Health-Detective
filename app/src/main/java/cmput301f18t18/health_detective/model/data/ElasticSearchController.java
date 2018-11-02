package cmput301f18t18.health_detective.model.data;

import cmput301f18t18.health_detective.model.domain.Problem;
import cmput301f18t18.health_detective.model.domain.ProblemRepo;
import cmput301f18t18.health_detective.model.domain.Record;
import cmput301f18t18.health_detective.model.domain.RecordRepo;
import cmput301f18t18.health_detective.model.domain.User;
import cmput301f18t18.health_detective.model.domain.UserRepo;

public class ElasticSearchController implements ProblemRepo, RecordRepo, UserRepo {
    private static final ElasticSearchController ourInstance = new ElasticSearchController();

    public static ElasticSearchController getInstance() {
        return ourInstance;
    }

    private ElasticSearchController() {
    }

    @Override
    public Problem getProblem(int problemID) {
        return null;
    }

    @Override
    public void removeProblem(int problemID) {

    }

    @Override
    public void saveProblem(Problem problem) {

    }

    @Override
    public Record getRecord(int recordID) {
        return null;
    }

    @Override
    public void removeRecord(int recordID) {

    }

    @Override
    public void saveRecord(Record record) {

    }

    @Override
    public User getUser(String uuid) {
        return null;
    }

    @Override
    public void saveUser(User usr) {

    }

    @Override
    public void deleteUser(String uuid) {

    }
}
