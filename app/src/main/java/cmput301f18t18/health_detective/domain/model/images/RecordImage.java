package cmput301f18t18.health_detective.domain.model.images;

import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;
import cmput301f18t18.health_detective.domain.model.images.impl.DomainImageImpl;

/**
 * Class to store photo data. Need to be remade.
 */
public class RecordImage {

    private BodyLocation bodyLocation;
    private DomainImage image;

    public RecordImage() {
        super();

        this.setBodyLocation(null);
    }

    public RecordImage(byte[] image) {

        this.setImage(image);
        this.setBodyLocation(null);
    }

    public RecordImage(DomainImage image) {

        this.setImage(image);
        this.setBodyLocation(null);
    }


    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public void setImage(byte[] image) {
        this.image = new DomainImageImpl(image);
    }

    public void setImage(DomainImage image) {
        this.image = image;
    }

    public byte[] getImage() {
        byte[] image = this.image.getImage();

        return image;
    }
}
