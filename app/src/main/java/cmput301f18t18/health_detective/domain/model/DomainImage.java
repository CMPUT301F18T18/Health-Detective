package cmput301f18t18.health_detective.domain.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.util.Id;

public class DomainImage {

    private String imageId;
    private String label;
    private Integer xPos;
    private Integer yPos;
    private byte[] image;

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

    public DomainImage(String id, byte[] image) {
        this(image);

        this.setImageId(id);
    }

    private void setImageId(String imageId) {
        this.imageId = imageId;
    }

    private void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageId() {
        return  this.imageId;
    }

    public byte[] getImage() {
        return this.image;
    }

    public String getLabel() {
        return label;
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
}
