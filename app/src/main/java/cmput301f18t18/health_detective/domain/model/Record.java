package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Record implements Searchable {

    public int recordId;
    private String title;
    private String comment;
    private Date date;
    private Geolocation location;
    private Set<BodyLocation> bodyLocations;
    private Set<Photo> photos;

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

    public void addBodyLocation(BodyLocation loc) {
        bodyLocations.add(loc);
    }

    public void setLocation(Geolocation location) {
        this.location = location;
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
