package cmput301f18t18.health_detective.domain.model.images.impl;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;
import cmput301f18t18.health_detective.domain.util.Id;

public class DomainImageImpl implements DomainImage {

    private String imageId;
    private String label;
    private String user;
    private Integer xPos;
    private Integer yPos;
    private byte[] image;

    public DomainImageImpl(String user) {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        if (newId == null) {
            newId = "";

        }

        this.setImageId(newId);
        this.setImage(new byte[]{});
    }

    public DomainImageImpl(String user, byte[] image) {
        this(user);

        this.setImage(image);
    }

    public DomainImageImpl(String id, String user, byte[] image) {
        this(user, image);

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

    public String getLabel() {
        return label;
    }

    public String getUser() {
        return user;
    }

    public void setPos(Integer xPos, Integer yPos) {
        if (xPos < 0) {
            xPos = 0;
        }
        else if (yPos < 0) {
            yPos = 0;
        }

        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Integer getxPos() {
        return xPos;
    }

    public Integer getyPos() {
        return yPos;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
