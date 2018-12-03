package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecord;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;

import static org.junit.Assert.*;

public class DeleteRecordImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private DeleteRecordMockPresenter callback;
    private ProblemRepo problems;
    private RecordRepo records;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.problems = new ProblemRepoMock();
        this.records = new RecordRepoMock();
        this.callback = new DeleteRecordMockPresenter();

        Date date = new Date();
        Problem problem1 = new Problem(
                "1234",
                "title1",
                "description1",
                date
        );
        problems.insertProblem(problem1);

        Record record1 = new Record(
                "2222",
                "title2",
                "description2",
                "author",
                date,
                null
        );
        Record record2 = new Record(
                "2222",
                "title2",
                "description2",
                "author",
                date,
                null
        );
        records.insertRecord(record1);
        records.insertRecord(record2);
        problem1.addRecord(record1);
        problem1.addRecord(record2);
    }

    // Testing if the record is correctly deleted
    @Test
    public void testDRRecord() {

        DeleteRecord command = new DeleteRecordImpl(
                callback,
                records.retrieveRecordById("1111")
        );
        command.execute();

        // Check to make sure record is deleted
        assertTrue(callback.isDRSuccessRecord());

        Record record = callback.getDeletedRecord();

        // Check to make sure record deleted is not null
        assertNotNull(record);

        // Check to make sure record has been removed from repo
        assertEquals(records.retrieveRecordById(record.getRecordId()), null);

        // Check record has been removed from problem
        assertFalse(problems.retrieveProblemById("1234").getRecordIds().contains(1111));

        // Check to make sure non-deleted record is still in repo
        assertFalse(records.retrieveRecordById("2222").equals(null));

        // Check to make sure non-deleted record is still in problem's list
        assertTrue(problems.retrieveProblemById("1234").getRecordIds().contains(2222));
    }

    // Testing to make sure cannot find problem check is working
    @Test
    public void testDRProblemNotFound() {

        Date date = new Date();
        Problem problem = new Problem(
                "3333",
                "Title",
                "Description",
                date
        );
        DeleteRecord command = new DeleteRecordImpl(
                callback,
                records.retrieveRecordById("1111")
        );
        command.execute();

        assertTrue(callback.isDRUnfoundProblem());
    }

    // Testing to make sure cannot find record check is working
    @Test
    public void testDRRecordNotFound() {

        Date date = new Date();
        Record record = new Record(
                "4444",
                "title2",
                "description2",
                "author",
                date,
                null
        );
        DeleteRecord command = new DeleteRecordImpl(
                callback,
                record
        );
        command.execute();

        assertTrue(callback.isDRUnfoundRecord());
    }
}


// Mock presenter to use for tests
class DeleteRecordMockPresenter implements DeleteRecord.Callback {

    private boolean DRSuccessRecord = false;
    private boolean nullRecord = false;
    private boolean nullProblem = false;
    private Record deletedRecord = null;

    public boolean isDRSuccessRecord() {
        return DRSuccessRecord;
    }

    public boolean isDRUnfoundProblem() {
        return nullProblem;
    }

    public boolean isDRUnfoundRecord() {
        return nullRecord;
    }

    public Record getDeletedRecord() {
        return deletedRecord;
    }

    @Override
    public void onDRSuccess(Record record) {
        this.DRSuccessRecord = true;
        this.deletedRecord = record;
    }

    @Override
    public void onDRProblemNotFound() {
        this.nullProblem = true;
    }

    @Override
    public void onDRRecordNotFound() {
        this.nullRecord = true;
    }

    @Override
    public void onDRFail() {
    }
}