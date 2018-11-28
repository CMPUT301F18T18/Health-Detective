package cmput301f18t18.health_detective.domain.model.images.impl;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;

public class DomainImageProxy implements DomainImage {

    private String imageId;
    private DomainImage image;

    public DomainImageProxy(String imageId) {
        this.setImageId(imageId);
    }

    private void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public String getImageId() {
        return imageId;
    }

    @Override
    public byte[] getImage() {
        if (this.image != null) {
            return this.image.getImage();
        }

        DomainContext context = DomainContext.getInstance();
        context.get
    }
}
