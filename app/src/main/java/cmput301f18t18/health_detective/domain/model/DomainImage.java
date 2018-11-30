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
    private String user;
    private Integer xPos;
    private Integer yPos;
    private byte[] image;

    public DomainImage(String user) {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        if (newId == null) {
            newId = "";

        }

        this.setImageId(newId);
        this.setImage(new byte[]{});
    }

    public DomainImage(String user, byte[] image) {
        this(user);

        this.setImage(image);
    }

    public DomainImage(String id, String user, byte[] image) {
        this(user, image);

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

    // converting photo stuff
    //https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
    public byte[] toBase64String(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        return byteArray;

    }

    public Bitmap toBitmap(String base64String){
        byte[] decodedBytes = Base64.decode(
                base64String,
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public String byteArrayToString(byte [] byteArray){
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
