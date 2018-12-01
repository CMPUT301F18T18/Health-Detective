package cmput301f18t18.health_detective.domain.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class ProblemTest {

    @Test
    public void containsBodyPart() {
    }

    @Test
    public void containsKeyword() {
    }

    @Test
    public void getProblemID() {
        Problem problem = new Problem("1");

        assertEquals("1", problem.getProblemId());
    }

    @Test
    public void getDescription() {
        Problem problem = new Problem(
                "Title",
                "Description"
        );

        assertEquals("Description", problem.getDescription());
    }

    @Test
    public void getStartDate() {
        Date date = new Date();
        Problem problem = new Problem(
                "Title",
                "",
                date
        );

        Date date2 = (Date) date.clone();

        assertEquals(date2, problem.getStartDate());
    }

    @Test
    public void getTitle() {
        Problem problem = new Problem("Title", "Description");

        assertEquals("Title", problem.getTitle());
    }

    @Test
    public void setDescription() {
        Problem problem = new Problem("Title", "Description");

        problem.setDescription("NewComment");

        assertEquals("NewComment", problem.getDescription());
    }

    @Test
    public void setStartDate() {
        Date date = new Date();
        Problem problem = new Problem();

        problem.setStartDate(date);
        Date date2 = (Date) date.clone();

        assertEquals(date2, problem.getStartDate());
    }

    @Test
    public void setTitle() {
        Problem problem = new Problem("Title", "Description");

        problem.setTitle("NewTitle");

        assertEquals("NewTitle", problem.getTitle());
    }

    @Test
    public void equals() {
        Problem problem = new Problem(
                "1",
                "Title",
                "Description",
                new Date()
        );

        Problem problem2 = new Problem(
                "1",
                "Title",
                "Description",
                new Date()
        );

        assertTrue(problem.equals(problem2));
    }

    @Test
    public void equals_DifferentTitle() {
        Problem problem = new Problem(
                "1",
                "Title",
                "Description",
                new Date()
        );

        Problem problem2 = new Problem(
                "1",
                "Title2",
                "Description",
                new Date()
        );

        assertTrue(problem.equals(problem2));
    }

    @Test
    public void equals_DifferentDescription() {
        Problem problem = new Problem(
                "1",
                "Title",
                "Description",
                new Date()
        );

        Problem problem2 = new Problem(
                "1",
                "Title",
                "Comment2",
                new Date()
        );

        assertTrue(problem.equals(problem2));
    }

    @Test
    public void equals_DifferentProblemId() {
        Date date = new Date();
        Problem problem = new Problem(
                "1",
                "Title",
                "Description",
                date
        );

        Problem problem2 = new Problem(
                "2",
                "Title",
                "Description",
                date
        );

        assertFalse(problem.equals(problem2));
    }

    @Test
    public void equals_SameObject() {
        Date date = new Date();
        Problem problem = new Problem(
                "1",
                "Title",
                "Description",
                date
        );

        assertTrue(problem.equals(problem));
    }

    @Test
    public void equals_NotAProblem() {
        Date date = new Date();
        Problem problem = new Problem(
                "1",
                "Title",
                "Description",
                date
        );

        assertFalse(problem.equals(new Date()));
    }

    @Test
    public void addRecordWithRecordObj() {
        Problem problem = new Problem();
        Record record = new Record(
                "1",
                "Title",
                "Description"
        );

        problem.addRecord(record);

        ArrayList<String> expectedRecordIds = new ArrayList<>();
        ArrayList<String> recordIds = problem.getRecordIds();

        expectedRecordIds.add("1");

        assertEquals(expectedRecordIds, recordIds);
    }

    @Test
    public void addRecordAddRecordById() {
        Problem problem = new Problem();

        problem.addRecord("1");

        ArrayList<String> expectedRecordIds = new ArrayList<>();
        ArrayList<String> recordIds = problem.getRecordIds();


        expectedRecordIds.add("1");

        assertEquals(expectedRecordIds, recordIds);
    }

    @Test
    public void removeRecordByObj() {
        Problem problem = new Problem();
        Record record = new Record(
                "1",
                "Title",
                "Description"
        );

        problem.addRecord("1");
        problem.removeRecord(record);

        ArrayList<String> expectedRecordIds = new ArrayList<>();
        ArrayList<String> recordIds = problem.getRecordIds();

        assertEquals(expectedRecordIds, recordIds);
    }

    @Test
    public void removeRecordByRecordId() {
        Problem problem = new Problem();
        Record record = new Record(
                "1",
                "Title",
                "Description"
        );

        problem.addRecord(record);
        problem.removeRecord("1");

        ArrayList<String> expectedRecordIds = new ArrayList<>();
        ArrayList<String> recordIds = problem.getRecordIds();

        assertEquals(expectedRecordIds, recordIds);
    }

    @Test
    public void isRecordsEmpty() {
        Problem problem = new Problem();

        assertTrue(problem.isRecordsEmpty());
    }

    @Test
    public void isRecordsEmpty_AddingARecord() {
        Problem problem = new Problem();
        problem.addRecord("1");

        assertFalse(problem.isRecordsEmpty());
    }

    @Test
    public void isRecordsEmpty_AfterRemoving() {
        Problem problem = new Problem();

        problem.addRecord("1");
        problem.addRecord("2");
        problem.removeRecord("1");
        problem.removeRecord("2");

        assertTrue(problem.isRecordsEmpty());
    }

    @Test
    public void getRecordIds() {
        Problem problem = new Problem();

        problem.addRecord("1");
        problem.addRecord("2000");
        problem.addRecord("100");

        ArrayList<String> recordIds = problem.getRecordIds();

        assertTrue(recordIds.contains("1"));
        assertTrue(recordIds.contains("2000"));
        assertTrue(recordIds.contains("100"));
    }
}