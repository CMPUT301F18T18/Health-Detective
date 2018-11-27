package cmput301f18t18.health_detective.domain.model.images;

import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;

/**
 * Class to store photo data. Need to be remade.
 */
public class RecordImage extends DomainImage {

    private BodyLocation bodyLocation;

    public RecordImage() {
        super();

        this.setBodyLocation(null);
    }

    public RecordImage(byte[] image) {
        super(image);

        this.setBodyLocation(null);
    }


    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }
}
