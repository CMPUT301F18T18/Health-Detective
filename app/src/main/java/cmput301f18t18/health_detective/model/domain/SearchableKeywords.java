package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;

public interface SearchableKeywords extends Searchable{
    boolean contains(ArrayList<String> keywords);
}