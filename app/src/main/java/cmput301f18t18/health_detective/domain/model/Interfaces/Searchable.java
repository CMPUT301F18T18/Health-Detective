package cmput301f18t18.health_detective.domain.model.Interfaces;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.BodyPart;

public interface Searchable {
    boolean containsKeyword(ArrayList<String> keywords);
    boolean containsBodyPart(BodyPart bodypart);
}
