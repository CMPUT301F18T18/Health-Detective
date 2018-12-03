package cmput301f18t18.health_detective.presentation.view.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//TODO: Make the all photo section increase with each photo addition

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cmput301f18t18.health_detective.DatePickerFragment;
import cmput301f18t18.health_detective.EditDialog;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.TimePickerFragment;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CameraPresenter;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.RecordViewPresenter;

public class PatientRecordViewActivity extends AppCompatActivity implements View.OnClickListener, RecordViewPresenter.View, EditDialog.ExampleDialogListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnMapReadyCallback {

    LinearLayout bodyPhotoScroll;
    RecordViewPresenter recordViewPresenter;
    TextView recordTitle, recordDate, recordDesc, backBLTag, frontBLTag;
    Button editMap;
    ImageView frontBL, backBL, addNPhoto;
    byte[] image;
    String userId = "";
    String title;
    String comment;
    Date date;
    Geolocation geolocation = null;
    DomainImage bodylocationOne = null;
    DomainImage bodylocationTwo = null;
    int testImages;
    private boolean LocationPermissionsGranted;
    private GoogleMap mMap;
    private int REQUEST_CODE = 1212;
    Boolean userType;
    private DateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY hh:mma");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);



        if (image == null){
            testImages = 0;
        } else {
            testImages = 1;
        }

        frontBL = findViewById(R.id.frontBL);
        backBL = findViewById(R.id.backBL);
        backBLTag = findViewById(R.id.backTag);
        frontBLTag = findViewById(R.id.frontTag);
        addNPhoto = findViewById(R.id.addNPhoto);
        recordTitle = findViewById(R.id.recTitle);
        recordDate = findViewById(R.id.recordDate);
        recordDesc = findViewById(R.id.commentView);
        editMap = findViewById(R.id.editmapbut);

        editMap.setOnClickListener(this);
        addNPhoto.setOnClickListener(this);
        frontBL.setOnClickListener(this);
        backBL.setOnClickListener(this);

        recordViewPresenter = new RecordViewPresenter(this);

        // Stuff for body location photos section
        bodyPhotoScroll = (LinearLayout) findViewById(R.id.root);

        //ImageView testImg = new ImageView(this);
        //testImg.setImageResource(R.drawable.ic_launcher_background);
        //setAllPhotoScroll();

        bodyPhotoScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(PatientRecordViewActivity.this, "BL Click", Toast.LENGTH_SHORT);
                Intent intent = new Intent(PatientRecordViewActivity.this, PhotoViewActivity.class);
                startActivity(intent);
            //toast.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        recordViewPresenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        if (userType){
            getMenuInflater().inflate(R.menu.logout_menu, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.edit_menu, menu);
            MenuItem userIdMenu = menu.findItem(R.id.userId);
            userIdMenu.setTitle(userId);
        }
//        getMenuInflater().inflate(R.menu.edit_menu, menu);
//        MenuItem userIdMenu = menu.findItem(R.id.userId);
//        userIdMenu.setTitle(userId);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent returnIntent = new Intent(this,PatientRecordsActivity.class);
        startActivity(returnIntent);

        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            case R.id.edit_title:
                String promptTitle = "Edit Title";
                openDialog(promptTitle,0,recordTitle.getText().toString());
                return true;
            case R.id.edit_date:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
                return true;
            case R.id.edit_desc:
                String promptDesc = "Edit Description";
                openDialog(promptDesc,2, recordDesc.getText().toString());
                return true;
            case R.id.edit_photo:
                Intent camaraIntent = new Intent(this, CamaraActivity.class);
                startActivity(camaraIntent);
                return true;
            case R.id.userId:
                Intent userIdIntent = new Intent(this, SignUpActivity.class);
                startActivity(userIdIntent);
                return true;
            case R.id.logout_edit:
                recordViewPresenter.onLogout();
                return true;
            case R.id.Logout_option:
                recordViewPresenter.onLogout();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
//    private void setAllPhotoScroll(){
//        ImageView testImg = new ImageView(this);
//        //testImg.setImageResource(R.drawable.ic_launcher_background);
//        //bodyPhotoScroll.addView(testImg);
//        //bodyPhotoScroll.invalidate();
//    }
    public void setTextViews(){
        recordTitle.setText(title);
        recordDate.setText(dateFormat.format(date).replace("AM","am").replace("PM","pm"));
        recordDesc.setText(comment);
    }

    @Override
    public void onRecordImages(ArrayList<DomainImage> images) {
        bodyPhotoScroll.removeAllViews();

        for (int i = 0; i < images.size(); i++) {
            String stringImage = images.get(i).getImage();
            Bitmap bitmap = CameraPresenter.toBitmap(stringImage);
            
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);
            imageView.setTag(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag();
                    onPhotoClick(index);
                }
            });

            bodyPhotoScroll.addView(imageView);
        }

        bodyPhotoScroll.invalidate();
    }

    @Override
    public void onRecordDetails(String title, String comment, Date date, Geolocation geolocation) {
        if (title == null)
            title = "";
        if (comment == null)
            comment = "";

        this.title = title;
        this.comment = comment;
        this.date = date;
        this.geolocation = geolocation;
        if (geolocation!=null){
            getLocationPermission();
        }



        setTextViews();
    }

    @Override
    public void makeToast(String msg, int length) {
        Toast toast = Toast.makeText(PatientRecordViewActivity.this, msg, length);
        toast.show();
    }

    @Override
    public void onGetPatient(Patient patient) {
        userType = false;
    }

    @Override
    public void onGetCP(CareProvider careProvider) {
        userType = true;
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorCareProviderDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCareProvider)));
        addNPhoto.setVisibility(View.GONE);
        editMap.setVisibility(View.GONE);
    }

    @Override
    public void onLogout() {
        Intent logoutIntent = new Intent(this,MainActivity.class);
        startActivity(logoutIntent);
    }

    @Override
    public void noImages() {
        bodyPhotoScroll.removeAllViews();
        bodyPhotoScroll.invalidate();
    }

    private void openDialog(String prompt,int type,String recordInfo){
        EditDialog exampleDialog = new EditDialog(prompt,type,recordInfo);
        exampleDialog.show(getSupportFragmentManager(), "Edit Dialog");

    }

    @Override
    public void applyEditTitle(String editedText) {
        recordViewPresenter.editUserRecord(editedText, comment, date, geolocation, bodylocationOne, bodylocationTwo);
    }

    @Override
    public void applyEditDesc(String editedComment) {
        recordViewPresenter.editUserRecord(title, editedComment, date, geolocation, bodylocationOne, bodylocationTwo);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = c.getTime();
        // recordViewPresenter.editUserRecord(title, comment, currentDate, geolocation);
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        date = c.getTime();
        recordViewPresenter.editUserRecord(title, comment, date, geolocation,bodylocationOne, bodylocationTwo);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNPhoto:
                if (userType){}
                else {
                    Intent photoIntent = new Intent(this, CamaraActivity.class);
                    startActivity(photoIntent);
                }
                break;
            case R.id.frontBL:
                if (userType){}
                else {
                    Intent camaraIntent = new Intent(this, CamaraActivity.class);
                    camaraIntent.putExtra("TYPE", true);
                    startActivity(camaraIntent);
                }
                //dialog box for add new front body location photo
                break;
            case R.id.backBL:
                if (userType) {}
                else {
                    Intent backBlIntent = new Intent(this, CamaraActivity.class);
                    backBlIntent.putExtra("TYPE", true);
                    backBlIntent.putExtra("LEFTRIGHT", true);
                    startActivity(backBlIntent);
                }
                //dialog box for add new back body location photo
                break;
            case R.id.editmapbut:
                if (userType){}
                else {
                    Intent mapIntent = new Intent(this, MapActivity.class);
                    //mapIntent.putExtra("PATIENT", patientContext);
                    mapIntent.putExtra("type", 1);
                    if (geolocation == null) {
                        Double lat = 53.5444;
                        Double lng = -113.491;
                        geolocation = new Geolocation(lat, lng);
                    }
                    mapIntent.putExtra("location", geolocation);
                    startActivityForResult(mapIntent, REQUEST_CODE);
                }
                break;
        }
    }

    private void onPhotoClick(int index) {
        Intent intent = new Intent(PatientRecordViewActivity.this, PhotoViewActivity.class);
        intent.putExtra("SELECTED_PHOTO", index);
        startActivity(intent);
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1234);
            }
        }else{ActivityCompat.requestPermissions(this,permissions,1234);
        }

    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_record_display);
        assert mapFragment != null;
        mapFragment.getMapAsync( PatientRecordViewActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LocationPermissionsGranted = false;

        switch(requestCode){
            case 1234:
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            LocationPermissionsGranted = false;
                            return;
                        }
                    }
                    LocationPermissionsGranted = true;
                    //initMap();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(LocationPermissionsGranted) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);


            LatLng location = new LatLng(geolocation.getlatitude(), geolocation.getlongitude());
            moveCamera(location, 15f);
            createMarker(location, title);
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private void createMarker( LatLng currentLatLng,String title){

        mMap.addMarker(new MarkerOptions()
                .position(currentLatLng)
                .anchor(0.5f, 0.5f)
                .title(title));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if(resultCode == PatientRecordsActivity.RESULT_OK){
                double[] doubleArrayExtra = data.getDoubleArrayExtra("result");
                geolocation = new Geolocation(doubleArrayExtra[0],doubleArrayExtra[1]);
                recordViewPresenter.editUserRecord(title , comment, date, geolocation, bodylocationOne, bodylocationTwo);
                mMap.clear();
                moveCamera(new LatLng(geolocation.getlatitude(),geolocation.getlongitude()),15f);
                createMarker(new LatLng(geolocation.getlatitude(),geolocation.getlongitude()),title);
                // edit records geo

            }
            if (resultCode == PatientRecordsActivity.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.d("abcdefghi","failed");
            }
        }
    }

    @Override
    public void displayBodyimageOne(DomainImage image) {
        if (image == null)
            return;

        String stringImage = image.getImage();
        Bitmap bitmap = CameraPresenter.toBitmap(stringImage);

        if (image.getLabel() != null)
            frontBLTag.setText(image.getLabel());

        frontBL.setImageBitmap(bitmap);
        frontBL.invalidate();
    }

    @Override
    public void displayBodyimageTwo(DomainImage image) {
        if (image == null)
            return;

        String stringImage = image.getImage();
        Bitmap bitmap = CameraPresenter.toBitmap(stringImage);

        if (image.getLabel() != null)
            backBLTag.setText(image.getLabel());

        backBL.setImageBitmap(bitmap);
        backBL.invalidate();
    }

    private void init(){

    }

}
