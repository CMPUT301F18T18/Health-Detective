package cmput301f18t18.health_detective.model.domain;

import java.util.Set;

public class BodyLocation {

    private BodyPart Location;
    private Set<BodyPhoto> BodyPhotos;

    public BodyPart getLocation() {
        return Location;
    }

    public void setLocation(BodyPart location) {
        Location = location;
    }
}
