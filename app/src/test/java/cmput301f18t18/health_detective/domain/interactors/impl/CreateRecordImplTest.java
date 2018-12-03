package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;

import static org.junit.Assert.*;


public class CreateRecordImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private CreateRecordMockPresenter callback;
    private ProblemRepo problems;
    private RecordRepo records;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.problems = new ProblemRepoMock();
        this.records = new RecordRepoMock();
        this.callback = new CreateRecordMockPresenter();
        Date date = new Date();

        Problem problem1 = new Problem(
                "1234567",
                "title",
                "description",
                date
        );
        problems.insertProblem(problem1);
    }

    // Testing record is correctly created
    @Test
    public void testCRRecord() {
        String title = "Record title";
        String description = "Record description";
        Date date = new Date();
        String authorId = "Me";

        CreateRecord command = new CreateRecordImpl(
                callback,
                title,
                description,
                date,
                null
        );
        command.execute();

        // Check to make sure record is created
        assertTrue(callback.isCRSuccessRecord());

        Record record = callback.getCreatedRecord();

        // Check to make sure all the attributes of the record were correctly assigned
        assertNotNull(record);
        assertEquals("Record title", record.getTitle());
        assertEquals("Record description", record.getComment());

        // Check record repo has been updated
        assertEquals(records.retrieveRecordById(record.getRecordId()).getRecordId(), record.getRecordId());

        // Check record has been added to problem
        assertTrue(problems.retrieveProblemById("1234567").getRecordIds().contains(record.getRecordId()));
    }

    // Testing null title
    @Test
    public void testCRNullTitle(){
        String title = null;
        String description = "description";
        Date date = new Date();
        String authorId = "Me";

        CreateRecord command = new CreateRecordImpl(
                callback,
                title,
                description,
                date,
                null
        );
        command.execute();

        Record record = callback.getCreatedRecord();

        assertTrue(callback.isCRNullTitle());
    }
}

// Mock presenter to use for tests
class CreateRecordMockPresenter implements CreateRecord.Callback {

    private boolean CRSuccessRecord = false;
    private boolean nullTitle = false;
    private Record createdRecord = null;

    public boolean isCRSuccessRecord() {
        return CRSuccessRecord;
    }

    public boolean isCRNullTitle() {
        return nullTitle;
    }

    public Record getCreatedRecord() {
        return createdRecord;
    }


    @Override
    public void onCRSuccess(Record record) {
        this.CRSuccessRecord = true;
        this.createdRecord = record;
    }

    @Override
    public void onCRNullTitle() {
        this.nullTitle = true;
    }

    @Override
    public void onCRInvalidPermissions() {

    }

    @Override
    public void onCRNoGeolocationProvided() {

    }
}
