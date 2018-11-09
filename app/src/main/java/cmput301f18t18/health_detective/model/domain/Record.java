package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;
import java.util.Set;

public class Record implements Searchable {
    public int recordID;
    private String title;
    private String comment;
    private Geolocation location;
    private Set<BodyLocation> bodyLocations;
    private Set<Photo> photos;

    public Record() { }

    public Record(int RUID) {
        this();
        this.recordID = RUID;
    }

    public int getRecordID() {
        return recordID;
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
        return (this.recordID ==  record.getRecordID());
    }
}
