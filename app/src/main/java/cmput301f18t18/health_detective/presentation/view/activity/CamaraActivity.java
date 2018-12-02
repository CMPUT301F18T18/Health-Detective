package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CameraPresenter;

import static cmput301f18t18.health_detective.presentation.view.activity.PermissionRequest.verifyPermission;

public class CamaraActivity extends AppCompatActivity implements CameraPresenter.View, View.OnClickListener{

    private CameraPresenter presenter;
    private Uri imageFileUri;
    private int angle = 0;
    private ImageView takenPhoto;
    private int FROM_GALLERY = 2;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        Intent newIntent = this.getIntent();
        boolean type = newIntent.getBooleanExtra("TYPE", false);
        boolean leftRight = newIntent.getBooleanExtra("LEFTRIGHT", false);

        takenPhoto = (ImageView) findViewById(R.id.photoPlacer);

        Button saveBtn = findViewById(R.id.saveBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        ImageView button = (ImageView) findViewById(R.id.TakeAPhoto);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                takeAPhoto();
            }
        };
        button.setOnClickListener(listener);

        verifyPermission(this);

        this.presenter = new CameraPresenter(this, type, leftRight);

        takeAPhoto();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, PatientRecordViewActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera_menu, menu);
        MenuItem rotatePhoto = menu.findItem(R.id.rotate);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rotate:
                takenPhoto.setRotation(angle);
            default:
                return super.onOptionsItemSelected(item);

        }
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
      
        File imageFile = new File(folder, "imagetest.jpg");
        imageFileUri = Uri.fromFile(imageFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
  
    private void toGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUri);
                    int newWidth = 256;
                    int newHeight = 256;

                    bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
                    takenPhoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveBtn){
            if (bitmap != null)
                presenter.onImage(bitmap);
        }
        else if (v.getId() == R.id.cancelBtn) {
            onBackPressed();
        }
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

    @Override
    public void onDone() {
        Intent intent = new Intent(this, PatientRecordViewActivity.class);
        startActivity(intent);
    }
}
