package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;

public class Search {
//    public <T extends Searchable> ArrayList<T> searchKeywords(ArrayList<? extends SearchableKeywords> toSearch, ArrayList<String> keywords) {
//        return null;
//    }
//
//    public <T extends Searchable> ArrayList<T> searchBodyPart(ArrayList<? extends SearchableBodyPart> toSeach, ArrayList<String> bodyparts) {
//        return null;
//    }
//
//    public <T extends Searchable, E extends SearchableGeolocation> ArrayList<T> searchGeolocation(ArrayList<E> toSearch, Geolocation geoloc) {
//        return null;
//    }

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