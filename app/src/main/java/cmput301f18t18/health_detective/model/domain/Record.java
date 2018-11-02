package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;
import java.util.Set;

public class Record implements Searchable {
    public int RecordID;
    private String Title;
    private String Comment;
    private Geolocation Location;
    private Set<BodyLocation> BodyLocations;
    private Set<Photo> Photos;

    @Override
    public boolean containsBodyPart(BodyPart bodyPart) {
        return false;
    }

    @Override
    public boolean containsKeyword(ArrayList<String> keywords) {
        return false;
    }
}
