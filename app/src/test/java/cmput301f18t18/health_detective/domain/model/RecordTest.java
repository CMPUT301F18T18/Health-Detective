package cmput301f18t18.health_detective.domain.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class RecordTest {

    @Test
    public void getRecordId() {
        Record record = new Record(
                1,
                "Title",
                "Comment"
        );

        assertEquals(1, record.getRecordId());
    }

    @Test
    public void getComment() {
        Record record = new Record(
                "Title",
                "Comment"
        );

        assertEquals("Comment", record.getComment());
    }

    @Test
    public void getDate() {
        Date date = new Date();
        Record record = new Record(
                "Title",
                "Comment",
                date
        );

        Date date2 = (Date) date.clone();

        assertEquals(date2, record.getDate());
    }

    @Test
    public void getTitle() {
        Record record = new Record("Title", "Comment");

        assertEquals("Title", record.getTitle());
    }

    @Test
    public void setComment() {
        Record record = new Record("Title", "Comment");

        record.setComment("NewComment");

        assertEquals("NewComment", record.getComment());
    }

    @Test
    public void setDate() {
        Date date = new Date();
        Record record = new Record();

        record.setDate(date);
        Date date2 = (Date) date.clone();

        assertEquals(date2, record.getDate());
    }

    @Test
    public void setTitle() {
        Record record = new Record("Title", "Comment");

        record.setTitle("NewTitle");

        assertEquals("NewTitle", record.getTitle());
    }

    @Test
    public void containsBodyPart() {

    }

    @Test
    public void containsKeyword() {
    }

    @Test
    public void equals() {
        Record record = new Record(
                1,
                "Title",
                "Comment",
                new Date()
        );

        Record record2 = new Record(
                1,
                "Title",
                "Comment",
                new Date()
        );

        assertTrue(record.equals(record2));
    }

    @Test
    public void equals_DifferentTitle() {
        Record record = new Record(
                1,
                "Title",
                "Comment",
                new Date()
        );

        Record record2 = new Record(
                1,
                "Title2",
                "Comment",
                new Date()
        );

        assertTrue(record.equals(record2));
    }

    @Test
    public void equals_DifferentComment() {
        Record record = new Record(
                1,
                "Title",
                "Comment",
                new Date()
        );

        Record record2 = new Record(
                1,
                "Title",
                "Comment2",
                new Date()
        );

        assertTrue(record.equals(record2));
    }

    @Test
    public void equals_DifferentRecordId() {
        Date date = new Date();
        Record record = new Record(
                1,
                "Title",
                "Comment",
                date
        );

        Record record2 = new Record(
                2,
                "Title",
                "Comment",
                 date
        );

        assertFalse(record.equals(record2));
    }

    @Test
    public void equals_SameObject() {
        Date date = new Date();
        Record record = new Record(
                1,
                "Title",
                "Comment",
                date
        );

        assertTrue(record.equals(record));
    }

    @Test
    public void equals_NotARecord() {
        Date date = new Date();
        Record record = new Record(
                1,
                "Title",
                "Comment",
                date
        );

        assertFalse(record.equals(new Date()));
    }
}