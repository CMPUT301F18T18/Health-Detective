package cmput301f18t18.health_detective.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.model.Interfaces.Searchable;

public class Record implements Searchable, Serializable {
    private static final long serialVersionUID = 2L;
    public int recordId;
    private String title;
    private String comment;
    private Date date;
    // Body location
    // geolocation
    // photo's

    public Record() {
        Date createDate = new Date();
        this.recordId = createDate.hashCode();
        this.setTitle(null);
        this.setComment(null);
        this.setDate(createDate);
    }

    public Record(String title, String comment) {
        Date createDate = new Date();
        this.recordId = createDate.hashCode();
        this.setTitle(title);
        this.setComment(comment);
        this.setDate(createDate);
    }

    public Record(int recordId, String title, String comment) {
        this.recordId = recordId;
        this.setTitle(title);
        this.setComment(comment);
        this.setDate(new Date());
    }

    public Record(String title, String comment, Date date) {
        Date createDate = new Date();
        this.recordId = createDate.hashCode();
        this.setTitle(title);
        this.setComment(comment);
        this.setDate(date);
    }

    public Record(int recordId, String title, String comment, Date date) {
        this.recordId = recordId;
        this.setTitle(title);
        this.setComment(comment);
        this.setDate(date);
    }

    public int getRecordId() {
        return recordId;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean containsBodyPart(BodyPart bodyPart) {
        return false;
    }

    @Override
    public boolean containsKeyword(ArrayList<String> keywords) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Record))
            return false;

        Record record = (Record) o;
        return (this.recordId ==  record.getRecordId());
    }
}
