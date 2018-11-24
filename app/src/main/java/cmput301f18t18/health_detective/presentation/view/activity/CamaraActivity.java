package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Method;

import cmput301f18t18.health_detective.R;
import static cmput301f18t18.health_detective.presentation.view.activity.PermissionRequest.verifyPermission;

public class CamaraActivity extends AppCompatActivity {

    Uri imageFileUri;
    int angle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        ImageView button = (ImageView) findViewById(R.id.TakeAPhoto);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v){
                takeAPhoto();
            }
        };
        button.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    public void takeAPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }

        //if(Build.VERSION.SDK_INT>=16) {
        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //}

        verifyPermission(this);

        String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + "jpg";

        File imageFile = new File(folder,"imagetest.jpg");
        imageFileUri = Uri.fromFile(imageFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            TextView tv = (TextView) findViewById(R.id.status);
//            if (resultCode == RESULT_OK) {
//                tv.setText("Photo OK!");

            //https://stackoverflow.com/questions/8981845/android-rotate-image-in-imageview-by-an-angle
                ImageView button = (ImageView) findViewById(R.id.photoPlacer);
                button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
                //int angle = 0;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        angle = angle + 90;
                        button.setRotation(angle);
                    }
                });
//                PhotoViewAttacher pAttacher;
//                pAttacher = new PhotoViewAttacher(Your_Image_View);
//                pAttacher.update();

//            } else if (resultCode == RESULT_CANCELED) {
//                tv.setText("Photo canceled");
//            } else {
//                tv.setText("Not sure what happened!" + resultCode);
//            }
        }
    }
}
