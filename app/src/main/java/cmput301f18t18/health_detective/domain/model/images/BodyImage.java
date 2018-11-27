package cmput301f18t18.health_detective.domain.model.images;

import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;

/**
 * Class to represent a body photo. Needs to be redone.
 */
public class BodyImage {

    private String id;
    private DomainImage frontImage;
    private DomainImage backImage;

    public BodyImage(DomainImage frontImage, DomainImage backImage) {
        this.setFrontImage(frontImage);
        this.setBackImage(backImage);
    }

    public BodyImage(String id, DomainImage frontImage, DomainImage backImage) {
        this(frontImage, backImage);

        this.setId(id);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DomainImage getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(DomainImage frontImage) {
        this.frontImage = frontImage;
    }

    public DomainImage getBackImage() {
        return backImage;
    }

    public void setBackImage(DomainImage backImage) {
        this.backImage = backImage;
    }
}
