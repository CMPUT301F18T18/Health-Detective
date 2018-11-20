package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.*;

public class GetRecordsImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private CreateUserProfileMockPresenter callback;
    private RecordRepo records;
    private ProblemRepo problems;

    @Before
    public void setUp() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.records = new RecordRepoMock();
        this.problems = new ProblemRepoMock();
        this.callback = new CreateUserProfileMockPresenter();
    }

    @Test
    public void problemHasRecords() {

    }

    @Test
    public void problemHasOneRecord() {}

    @Test
    public void patientHasNoRecords() {

    }

}

class GetRecordsMockPresenter implements GetRecords.Callback {

    private boolean isSuccess = false;
    private  boolean isNoRecords = false;
    private ArrayList<Record> retrievedRecords = null;

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isNoRecords() {
        return isNoRecords;
    }

    public ArrayList<Record> getRetrievedRecords() {
        return retrievedRecords;
    }

    @Override
    public void onGRSuccess(ArrayList<Record> records) {
        this.isSuccess = true;
        this.retrievedRecords = records;
    }

    @Override
    public void onGRNoRecords() {
        this.isNoRecords = true;
    }
}