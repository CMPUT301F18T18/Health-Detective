package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class CameraPresenter {










    //TODO: this will convert to the byte array if that's how you want to store it
    private byte[] toByteArray(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        return byteArray;

    }
}
