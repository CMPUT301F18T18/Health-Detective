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
    private String frontImageId;
    private String backImageId;


    public BodyLocation(String user, String label, String frontImage, String backImage) {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        this.user = user;
        this.setId(newId);
        this.setLabel(label);
        this.setFrontImageId(frontImage);
        this.setBackImageId(backImage);
    }

    public BodyLocation(String user, String id, String label, String frontImage, String backImage) {
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


    public String getUser() {
        return user;
    }

    public String getFrontImageId() {
        return frontImageId;
    }

    public void setFrontImageId(String frontImageId) {
        this.frontImageId = frontImageId;
    }

    public String getBackImageId() {
        return backImageId;
    }

    public void setBackImageId(String backImageId) {
        this.backImageId = backImageId;
    }
}
