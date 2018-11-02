package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;

public interface Searchable {
    boolean containsKeyword(ArrayList<String> keywords);
    boolean containsBodyPart(BodyPart bodypart);
}
