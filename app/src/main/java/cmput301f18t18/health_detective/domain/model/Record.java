package cmput301f18t18.health_detective.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.interfaces.Searchable;
import cmput301f18t18.health_detective.domain.util.Id;

/**
 * The class to store data and methods related to individual records.
 */
public class Record implements Searchable, Serializable {
    private static final long serialVersionUID = 2L;
    private String recordId;
    private String title;
    private String comment;
    private Date date;
    private ArrayList<String> photos;
    private Geolocation geolocation;
    private String author;

    public Record() {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        this.recordId = newId;
        this.setTitle(null);
        this.setComment(null);
        this.setDate(new Date());
        this.setAuthor("");
    }

    public Record(String id) {
        this();
        this.recordId = id;
    }

    public Record(String title, String comment) {
        this();
        this.setTitle(title);
        this.setComment(comment);
    }

    public Record(String title, String comment, Date date) {
        this(title, comment);
        this.setDate(date);
    }

    public Record(String title, String comment, String author) {
        this(title, comment);
        this.setAuthor(author);
    }

    public Record(String recordId, String title, String comment, String author, Date date, Geolocation geolocation) {
        this(title, comment, date);
        this.recordId = recordId;
        setAuthor(author);
        setGeolocation(geolocation);
    }

    public String getRecordId() {
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
        return (this.recordId.equals(record.getRecordId()));
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void insertPhoto(DomainImage image) {
        photos.add(image.getImageId());
    }

    public void deletePhoto(DomainImage image) {
        photos.remove(image.getImageId());
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
