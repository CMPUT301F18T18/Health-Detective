package cmput301f18t18.health_detective.domain.model.images.impl;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;
import cmput301f18t18.health_detective.domain.util.Id;

public class DomainImageImpl implements DomainImage {

    protected String imageId;
    protected byte[] image;

    public DomainImageImpl() {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        if (newId == null) {
            newId = "";

        }

        this.setImageId(newId);
        this.setImage(new byte[]{});
    }

    public DomainImageImpl(byte[] image) {
        this();

        this.setImage(image);
    }

    public DomainImageImpl(String id, byte[] image) {
        this(image);

        this.setImageId(id);
    }

    private void setImageId(String imageId) {
        this.imageId = imageId;
    }

    private void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String getImageId() {
        return  this.imageId;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }
}
