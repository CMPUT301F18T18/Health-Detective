package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.ViewProblem;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;

import static org.junit.Assert.*;

public class ViewProblemImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private GetRecordsMockPresenter callback;
    private RecordRepo records;
    private ProblemRepo problems;

    @Before
    public void setUp() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.records = new RecordRepoMock();
        this.problems = new ProblemRepoMock();
        this.callback = new GetRecordsMockPresenter();
    }

    @Test
    public void problemHasRecords() {
        Date dateToUse1 = new Date();

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

        Date dateToUse2 = new Date();

        Problem testProblem = new Problem();
        Record testRecord1 = new Record("1","Title1", "Comment1", "Author", (Date) dateToUse1.clone(), null);
        Record testRecord2 = new Record("2","Title2","Comment2", "Author", (Date) dateToUse2.clone(), null);

        testProblem.addRecord(testRecord1);
        testProblem.addRecord(testRecord2);
        problems.insertProblem(testProblem);
        records.insertRecord(testRecord1);
        records.insertRecord(testRecord2);

        ViewProblem interactor = new ViewProblemImpl(
                callback
        );

        interactor.execute();

        ArrayList<Record> compareRecords = new ArrayList<>();
        Record compareRecord1 = new Record("1","Title1", "Comment1", "Author", (Date) dateToUse1.clone(), null);
        Record compareRecord2 = new Record("2", "Title2", "Comment2", "Author", (Date) dateToUse2.clone(), null);

        compareRecords.add(compareRecord2);
        compareRecords.add(compareRecord1);

        assertTrue(callback.isSuccess());
        assertFalse(callback.isNoRecords());

        assertEquals(compareRecords, callback.getRetrievedRecords());
    }

    @Test
    public void patientHasNoRecords() {

        Problem testProblem = new Problem();
        problems.insertProblem(testProblem);

        ViewProblem interactor = new ViewProblemImpl(
                callback
        );

        interactor.execute();

        assertTrue(callback.isNoRecords());
        assertFalse(callback.isSuccess());
    }

}

class GetRecordsMockPresenter implements ViewProblem.Callback {

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
    public void onVPSuccess(ArrayList<Record> records) {
        this.isSuccess = true;
        this.retrievedRecords = records;
    }

    @Override
    public void onVPSuccessDetails(String title, String description, Date date) {

    }

    @Override
    public void onVPNoRecords() {
        this.isNoRecords = true;
    }

    @Override
    public void onVPNoContext() {

    }
}