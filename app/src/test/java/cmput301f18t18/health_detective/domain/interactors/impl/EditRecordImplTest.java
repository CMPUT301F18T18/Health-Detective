package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.interactors.EditRecord;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;

import static org.junit.Assert.*;

public class EditRecordImplTest {
    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private EditRecordMockPresenter callback;
    private ProblemRepo problems;
    private RecordRepo records;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.problems = new ProblemRepoMock();
        this.records = new RecordRepoMock();
        this.callback = new EditRecordMockPresenter();
        Date date = new Date();

        Problem problem1 = new Problem(
                "1234567",
                "title",
                "description",
                date
        );
        problems.insertProblem(problem1);
    }

    // Testing record is correctly edited
    @Test
    public void testERRecord() {
        
        Record recordBefore = new Record();
        records.insertRecord(recordBefore);
        
        
        String title = "Record title";
        String description = "Record description";
        Date date = new Date();
        String authorId = "Me";

        EditRecord command = new EditRecordImpl(
                callback,
                title,
                description,
                date,
                null,
                null,
                null
        );
        command.execute();

        // Check to make sure record is edited
        assertTrue(callback.isERSuccessRecord());

        Record record = callback.getEditedRecord();

        // Check to make sure all the attributes of the record were correctly assigned
        assertNotNull(record);
        assertEquals("Record title", record.getTitle());
        assertEquals("Record description", record.getComment());

        // Check record repo has been updated
        assertEquals(records.retrieveRecordById(recordBefore.getRecordId()).getRecordId(), record.getRecordId());
    }

    @Test
    public void testERRecordNullComment() {

        Record recordBefore = new Record();
        records.insertRecord(recordBefore);


        String title = "Record title";
        String description = null;
        Date date = new Date();
        String authorId = "Me";

        EditRecord command = new EditRecordImpl(
                callback,
                title,
                description,
                date,
                null,
                null,
                null
        );
        command.execute();

        // Check to make sure record is edited
        assertTrue(callback.isERSuccessRecord());

        Record record = callback.getEditedRecord();

        // Check to make sure all the attributes of the record were correctly assigned
        assertNotNull(record);
        assertEquals("Record title", record.getTitle());
        assertEquals("", record.getComment());

        // Check record repo has been updated
        assertEquals(records.retrieveRecordById(recordBefore.getRecordId()).getRecordId(), record.getRecordId());
    }

    // Testing null title
    @Test
    public void testERNullTitle(){
        Record recordBefore = new Record();

        String title = null;
        String description = "description";
        Date date = new Date();
        String authorId = "Me";

        EditRecord command = new EditRecordImpl(
                callback,
                title,
                description,
                date,
                null,
                null,
                null
        );
        command.execute();

        assertTrue(callback.isNullTitle());
    }

    // Testing null title
    @Test
    public void testERNoDateTitle(){
        Record recordBefore = new Record();

        String title = "This is a title";
        String description = "description";
        Date date = null;
        String authorId = "Me";

        EditRecord command = new EditRecordImpl(
                callback,
                title,
                description,
                date,
                null,
                null,
                null
        );
        command.execute();

        assertTrue(callback.isNoDate());
    }
}

// Mock presenter to use for tests
class EditRecordMockPresenter implements EditRecord.Callback {

    private boolean ERSuccessRecord = false;
    private boolean nullTitle = false;
    private boolean noDate = false;
    private Record editedRecord = null;

    public boolean isERSuccessRecord() {
        return ERSuccessRecord;
    }

    public boolean isNoDate() {
        return noDate;
    }

    public boolean isNullTitle() {
        return nullTitle;
    }

    public Record getEditedRecord() {
        return editedRecord;
    }
    

    @Override
    public void onERSuccess(Record record) {
        this.ERSuccessRecord = true;
        this.editedRecord = record;
    }

    @Override
    public void onEREmptyTitle() {
        this.nullTitle = true;
    }

    @Override
    public void onERNoDateProvided() {
        this.noDate = true;
    }

    @Override
    public void onERNoGeolocationProvided() {

    }

    @Override
    public void onERInvalidPermissions() {

    }
}