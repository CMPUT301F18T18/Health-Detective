package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;

public class Search {

    public <T extends Searchable> ArrayList<T> searchKeywords(ArrayList<T> toSearch, ArrayList<String> keywords) {
        return null;
    }

    public <T extends Searchable> ArrayList<T> searchBodyPart(ArrayList<T> toSeach, BodyPart bodypart) {
        return null;
    }

    public <T extends Searchable> ArrayList<T> searchGeolocation(ArrayList<T> toSearch, Geolocation geoloc) {
        return null;
    }
}