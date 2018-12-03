package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecordPhoto;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.ViewRecord;
import cmput301f18t18.health_detective.domain.interactors.impl.DeletePhotoImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.ViewRecordImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Photo;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CameraPresenter;

public class PhotoViewActivity extends AppCompatActivity implements DeleteRecordPhoto.Callback, ViewRecord.Callback, GetLoggedInUser.Callback, CameraPresenter.View {

    private ArrayList<DomainImage> images = new ArrayList<>();
    private int index;
    private ImageView currentImage, deleteBtn;
    private Boolean userType;
    private CameraPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        Intent intent = getIntent();
        index = intent.getIntExtra("SELECTED_PHOTO", 0);

        currentImage = findViewById(R.id.currentImg);
        deleteBtn = findViewById(R.id.deleteImg);
        presenter = new CameraPresenter(this, false, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        new ViewRecordImpl(this).execute();
    }


    @Override
    public void onVRInvalidRecord() {

    }

    @Override
    public void onVRSuccessDetails(String title, String comment, Date date, Geolocation geolocation) {

    }

    @Override
    public void onVRSuccessImages(ArrayList<DomainImage> images) {
        this.images = images;

        updateImage();
    }

    @Override
    public void onVRBodyOne(DomainImage bodylocationOne) {

    }

    @Override
    public void onVRBodyTwo(DomainImage bodylocationTwo) {

    }

    @Override
    public void onVRNoImages() {
        finish();
    }

    public void updateImage() {
        String stringImage = images.get(index).getImage();
        Bitmap bitmap = CameraPresenter.toBitmap(stringImage);

        currentImage.setImageBitmap(bitmap);
    }

    public void deleteImage() {
        new DeletePhotoImpl(this, images.get(index)).execute();

        images.remove(index);

        if (images.isEmpty()) {
            finish();
            return;
        }

        else if (index >= images.size())
            index--;

        if (index < 0)
            index = 0;

        updateImage();
    }

    @Override
    public void onGLIUNoUserLoggedIn() {

    }

    @Override
    public void onGLIUPatient(Patient patient) { }

    @Override
    public void onGLIUCareProvider(CareProvider careProvider) { }

    private void init(){
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage();
            }
        });
        if (userType){
            deleteBtn.setVisibility(View.GONE);
        }

        currentImage.setOnTouchListener(new PhotoSlideShowSwiper(this){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (index > 0)
                    index--;
                else
                    index = 0;

                updateImage();
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (index < images.size())
                    index++;
                else
                    index = images.size();

                updateImage();
            }
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void onDone() {

    }

    @Override
    public void onPView() {
        userType = false;
        init();
    }

    @Override
    public void onCPView() {
        userType = true;
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorCareProviderDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCareProvider)));
        init();
    }
}
