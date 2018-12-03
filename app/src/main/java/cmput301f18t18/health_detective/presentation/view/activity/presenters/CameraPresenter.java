package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

import cmput301f18t18.health_detective.domain.interactors.AddPhoto;
import cmput301f18t18.health_detective.domain.interactors.impl.AddPhotoImpl;
import cmput301f18t18.health_detective.domain.model.DomainImage;

public class CameraPresenter implements AddPhoto.Callback {

    private View view;
    private boolean type;
    private boolean leftRight;

    public static Bitmap toBitmap(String base64String){
        byte[] decodedBytes = Base64.decode(
                base64String,
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String byteArrayToString(byte [] byteArray){
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onAPhInvalidPermissions() {

    }

    @Override
    public void onAPhNoImage() {

    }

    @Override
    public void onAPhSuccess(DomainImage image) {
        view.onDone();
    }

    public interface View {
        void onDone();
    }

    public CameraPresenter(View view, boolean type, boolean leftRight) {
        this.view = view;
        this.type = type;
        this.leftRight = leftRight;
    }

    public void onImage(Bitmap image) {
        byte[] byteImage = toByteArray(image);
        String base64String = byteArrayToString(byteImage);

        new AddPhotoImpl(this, "This Is a Label", base64String, type, leftRight).execute();
    }

    public void onSave() {

    }

    //TODO: this will convert to the byte array if that's how you want to store it
    private byte[] toByteArray(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        return byteArray;

    }
}
