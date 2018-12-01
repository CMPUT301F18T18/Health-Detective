package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Photo;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;

import static cmput301f18t18.health_detective.presentation.view.activity.PermissionRequest.verifyPermission;

public class CamaraActivity extends AppCompatActivity implements OnTouchListener, View.OnClickListener{

    Uri imageFileUri;
    int angle = 0;
    ImageView takenPhoto;
    private int FROM_GALLERY = 2;
    private static final String TAG = "Touch";
    String base;
    byte[] temp;
    Bitmap bitmap;

    // adding in trying to draw a goddamn circle
    private Paint pTouch;
    public int x;
    public int y;
    int radius = 5;
    Canvas canvas;


    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    Record record;
    Patient patientContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
      
        Intent newIntent = this.getIntent();
        this.record = (Record) newIntent.getSerializableExtra("RECORD");
        this.patientContext = (Patient) newIntent.getSerializableExtra("USER");



        takenPhoto = (ImageView) findViewById(R.id.photoPlacer);


        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);

        //adding circule shit
        pTouch = new Paint(Paint.ANTI_ALIAS_FLAG);
        // pTouch.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
        pTouch.setColor(Color.RED);
        pTouch.setStyle(Paint.Style.STROKE);

        pTouch.setStrokeWidth(5);



        takenPhoto.setOnTouchListener(this);

        ImageView button = (ImageView) findViewById(R.id.TakeAPhoto);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                takeAPhoto();
            }
        };
        button.setOnClickListener(listener);
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
                //https://stackoverflow.com/questions/8981845/android-rotate-image-in-imageview-by-an-angle
                //angle = angle + 90;
                //takenPhoto.setRotation(angle);
                //toGallery();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUri);
                    //byte[] tempArray = toByteArray(bitmap);
                    //TODO: pass bitmap to presenter and in presenter you convert to byte array
                    takenPhoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //takenPhoto.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            Drawable drawable = takenPhoto.getDrawable();
            Rect imageBounds = drawable.getBounds();


            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;



            Rect frameToDraw = new Rect(0, 0, width, height);
            RectF whereToDraw = new RectF(0, 0, width, height - 300);

            canvas.drawBitmap(bitmap,frameToDraw,whereToDraw, null);



//            Canvas canvas = new Canvas(bitmap);
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.FILL);
//            paint.setColor(Color.RED);
//            paint.setAntiAlias(true);
//            canvas.drawCircle(
//                    canvas.getWidth() / 2, // cx
//                    canvas.getHeight() / 2, // cy
//                    5, // Radius
//                    paint // Paint
//            );
//            takenPhoto.setImageBitmap(bitmap);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveBtn){
            Intent intent = new Intent(this, PatientRecordViewActivity.class);
            intent.putExtra("RECORD", record);
            intent.putExtra("USER", patientContext);
            //intent.putExtra("PHOTO", temp);
            startActivity(intent);
        }
    }

    // converting photo stuff
    //https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
//    public byte[] toByteArray(Bitmap bitmap){
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        byte[] byteArray = outputStream.toByteArray();
//
//        return byteArray;
//
//    }

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
