package cmput301f18t18.health_detective.domain.model.images.base;

import cmput301f18t18.health_detective.domain.model.DomainContext;
import cmput301f18t18.health_detective.domain.util.Id;

public class DomainImage {

    protected String imageId;
    protected byte[] image;

    public DomainImage() {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        if (newId == null) {
            newId = "";

        }

        this.setImageId(newId);
        this.setImage(new byte[]{});
    }

    public DomainImage(byte[] image) {
        this();

        this.setImage(image);
    }

    private void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageId() {
        return  this.imageId;
    }

    public byte[] getImage() {
        return this.image;
    }
}
