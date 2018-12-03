package cmput301f18t18.health_detective.domain.model;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.util.Id;


/**
 *  Stores an image with a label in base 64 string
 */
public class DomainImage {

    private String imageId;
    private String label;
    private String image;

    public DomainImage() {
        // Generate unique userid
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        if (newId == null) {
            newId = "";

        }

        this.setImageId(newId);
        this.setImage("");
    }

    public DomainImage(String image) {
        this();

        this.setImage(image);
    }

    public DomainImage(String id, String image) {
        this(image);

        this.setImageId(id);
    }

    private void setImageId(String imageId) {
        this.imageId = imageId;
    }

    private void setImage(String image) {
        this.image = image;
    }

    public String getImageId() {
        return  this.imageId;
    }

    public String getImage() {
        return this.image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label == null) {
            this.label = "";
            return;
        }

        this.label = label;
    }
}
