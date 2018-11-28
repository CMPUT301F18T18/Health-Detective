package cmput301f18t18.health_detective.domain.model.images;

import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;
import cmput301f18t18.health_detective.domain.model.images.impl.DomainImageImpl;

/**
 * Class to represent a body photo. Needs to be redone.
 */
public class BodyImage {

    private String id;
    private DomainImage frontImage;
    private DomainImage backImage;

    public BodyImage(DomainImageImpl frontImage, DomainImageImpl backImage) {
        this.setFrontImage(frontImage);
        this.setBackImage(backImage);


    }

    public BodyImage(String id, DomainImageImpl frontImage, DomainImageImpl backImage) {
        this(frontImage, backImage);

        this.setId(id);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public byte[] getFrontImage() {
        byte[] image = this.frontImage.getImage();

        return image;
    }

    public byte[] getBackImage() {
        byte[] image = this.backImage.getImage();

        return image;
    }

    public void setFrontImage(byte[] frontImage) {
        this.frontImage = new DomainImageImpl(frontImage);
    }

    public void setBackImage(byte[] backImage) {
        this.backImage = new DomainImageImpl(backImage);
    }

    public void setFrontImage(DomainImage frontImage) {
        this.frontImage = frontImage;
    }

    public void setBackImage(DomainImage backImage) {
        this.backImage = backImage;
    }
}
