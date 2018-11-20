package cmput301f18t18.health_detective.domain.model;

import java.util.Set;

/**
 * Class for BodyLocation data. Probably redundant. Needs to be redone.
 */
public class BodyLocation {

    private BodyPart location;
    private Set<BodyPhoto> bodyPhotos;

    public BodyPart getLocation() {
        return location;
    }

    public void setLocation(BodyPart location) {
        location = location;
    }

}
