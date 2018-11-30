package cmput301f18t18.health_detective.domain.model;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.util.Id;

/**
 * Class for BodyLocation data. Probably redundant. Needs to be redone.
 */
public class BodyLocation {

    private String id;
    private String user;
    private String label;
    private DomainImage frontImage;
    private DomainImage backImage;


    public BodyLocation(String user, String label, DomainImage frontImage, DomainImage backImage) {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        this.user = user;
        this.setId(newId);
        this.setLabel(label);
        this.setFrontImage(frontImage);
        this.setBackImage(backImage);
    }

    public BodyLocation(String user, String id, String label, DomainImage frontImage, DomainImage backImage) {
        this(user, label, frontImage, backImage);

        this.setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }

        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getFrontImage() {
        byte[] image = this.frontImage.getImage();

        return image;
    }

    public byte[] getBackImage() {
        byte[] image = this.backImage.getImage();

        return image;
    }

    public void setFrontImage(DomainImage frontImage) {
        this.frontImage = frontImage;
    }

    public void setBackImage(DomainImage backImage) {
        this.backImage = backImage;
    }

    public String getUser() {
        return user;
    }
}
