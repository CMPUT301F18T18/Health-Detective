package cmput301f18t18.health_detective.model.domain;

import java.util.Set;

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
