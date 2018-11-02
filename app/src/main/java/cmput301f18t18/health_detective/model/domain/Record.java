package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;
import java.util.Set;

public class Record implements SearchableKeywords, SearchableBodyPart {
    public int RecordID;
    private String Title;
    private String Comment;
    private Geolocation Location;
    private Set<BodyLocation> BodyLocations;
    private Set<Photo> Photos;

    @Override
    public boolean contains(BodyPart bodyPart) {
        return false;
    }

    @Override
    public boolean contains(ArrayList<String> keywords) {
        return false;
    }
}
