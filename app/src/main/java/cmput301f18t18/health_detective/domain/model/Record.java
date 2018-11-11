package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.Set;

public class Record implements Searchable {
    public int RecordID;
    private String Title;
    private String Comment;
    private Geolocation Location;
    private Set<BodyLocation> BodyLocations;
    private Set<Photo> Photos;

    public Record() { }

    public Record(int RUID) {
        this();
        this.RecordID = RUID;
    }

    public int getRecordID() {
        return RecordID;
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
        BodyLocations.add(loc);
    }

    public void setLocation(Geolocation location) {
        Location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Record))
            return false;

        Record record = (Record) o;
        return (this.RecordID ==  record.getRecordID());
    }
}
