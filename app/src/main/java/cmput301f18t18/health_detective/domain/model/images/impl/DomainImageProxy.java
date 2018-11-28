package cmput301f18t18.health_detective.domain.model.images.impl;

import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;

public class DomainImageProxy implements DomainImage {
    private String imageId;

    public DomainImageProxy(String imageId) {
        this.setImageId(imageId);
    }

    private void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public String getImageId() {
        return null;
    }

    @Override
    public byte[] getImage() {
        return new byte[0];
    }

    @Override
    public void setImage(byte[] image) {

    }
}
