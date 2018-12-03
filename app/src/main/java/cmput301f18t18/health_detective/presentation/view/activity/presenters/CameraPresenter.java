package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

import cmput301f18t18.health_detective.domain.interactors.AddPhoto;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.impl.AddPhotoImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetLoggedInUserImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Patient;

public class CameraPresenter implements AddPhoto.Callback, GetLoggedInUser.Callback {

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
        void onPView();
        void onCPView();
    }

    public CameraPresenter(View view, boolean type, boolean leftRight) {
        this.view = view;
        this.type = type;
        this.leftRight = leftRight;
        new GetLoggedInUserImpl(this).execute();
    }

    public void onImage(Bitmap image, String blLabel) {
        byte[] byteImage = toByteArray(image);
        String base64String = byteArrayToString(byteImage);

        new AddPhotoImpl(this, blLabel, base64String, type, leftRight).execute();
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

    @Override
    public void onGLIUNoUserLoggedIn() {

    }

    @Override
    public void onGLIUPatient(Patient patient) {
        this.view.onPView();
    }

    @Override
    public void onGLIUCareProvider(CareProvider careProvider) {
        this.view.onCPView();
    }

}
