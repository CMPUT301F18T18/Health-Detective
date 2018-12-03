package cmput301f18t18.health_detective.presentation.view.activity;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.Manifest;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.ViewProblem;
import cmput301f18t18.health_detective.domain.interactors.impl.GetLoggedInUserImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.PutContext;
import cmput301f18t18.health_detective.domain.interactors.impl.ViewProblemImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, ViewProblem.Callback, GetLoggedInUser.Callback, OnMapReadyCallback, View.OnClickListener{

    private float ZOOM = 15f;

    private Boolean LocationPermissionsGranted = false;
    private GoogleMap mMap;
    private EditText searchText;
    private int type;
    private Geolocation myLocation, startLocation;
    private Button Cancel, Save;
    private String userID = "user";
    private ArrayList<Record> recordlist = new ArrayList<Record>();
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private ArrayList<Integer> testclick = new ArrayList<Integer>();
    private Boolean userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        new GetLoggedInUserImpl(this).execute();
        Intent intent = this.getIntent();
        this.type = (int) intent.getSerializableExtra("type");
        this.startLocation = (Geolocation) intent.getSerializableExtra("location");
        Cancel = findViewById(R.id.MapCancel);
        Save = findViewById(R.id.MapSave);
        if (type == 0) {
            Save.setVisibility(View.INVISIBLE);
            Cancel.setVisibility(View.INVISIBLE);
            new ViewProblemImpl(this).execute();
        }

        searchText = findViewById(R.id.input_search);


        Cancel.setOnClickListener(this);
        Save.setOnClickListener(this);



        if (type == 1){
            getLocationPermission();
        }

    }




    private void init(){
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    locateSearch();
                }

                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (type == 1) {
                    mMap.clear();
                    createMarker(latLng, "new record");
                }
            }
        });
    }

    private void locateSearch(){
        String searchString = searchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try{
           list = geocoder.getFromLocationName(searchString, 1);
        }catch(IOException e){

        }
        if(list.size()>0){
            Address address = list.get(0);
            LatLng searchLocation = new LatLng(address.getLatitude(),address.getLongitude());
            moveCamera(searchLocation, ZOOM);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        MenuItem userIdMenu = menu.findItem(R.id.userId);
        userIdMenu.setTitle(userID);
        return true;
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapActivity.this);


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


    private void getDeviceLocation() {

        LatLng startLatLng = new LatLng(startLocation.getlatitude(), startLocation.getlongitude());
        moveCamera(startLatLng, ZOOM);
        if (type == 1) {
            Log.d("maptest", Double.toString(startLocation.getlatitude()));
            createMarker(startLatLng, "Location");
        }
        if (type == 0){
            if (this.recordlist == null){
                new ViewProblemImpl(this).execute();
            }

            for (int i = 0; i < this.recordlist.size();i++){
                Marker marker = createMarker(new LatLng(
                        recordlist.get(i).getGeolocation().getlatitude(),
                        recordlist.get(i).getGeolocation().getlongitude()),
                        recordlist.get(i).getTitle());
                testclick.add(0);
                markers.add(marker);

            }
        }
    }





    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
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
                    initMap();

                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent searchIntent = new Intent(this,SearchActivity.class);
                changeActivity(searchIntent);
                return true;

            case R.id.Logout_option:
                Intent logoutIntent = new Intent(this,MainActivity.class);
                changeActivity(logoutIntent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.userId:
                Intent userIdIntent = new Intent(this, SignUpActivity.class);
                startActivity(userIdIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

}

    private void changeActivity(Intent intent) {
        startActivity(intent);
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
            mMap.setOnMarkerClickListener(this);
            getDeviceLocation();
            init();
        }

    }

    private Marker createMarker( LatLng currentLatLng,String title){
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(currentLatLng)
                .anchor(0.5f, 0.5f)
                .title(title));

        myLocation = new Geolocation(currentLatLng.latitude,currentLatLng.longitude);

        return marker;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.MapCancel){
            finish();
        }
        if(v.getId()==R.id.MapSave){
            if (type == 1) {
                Intent returnIntent = new Intent();
                double[] array = new double[]{myLocation.getlatitude(), myLocation.getlongitude()};
                returnIntent.putExtra("result", array);
                setResult(PatientRecordsActivity.RESULT_OK, returnIntent);
            }
            finish();
        }
    }

    @Override
    public void onGLIUNoUserLoggedIn() {

    }

    @Override
    public void onGLIUPatient(Patient patient) {
        userID = patient.getUserId();
    }

    @Override
    public void onGLIUCareProvider(CareProvider careProvider) {
        userType = false;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCareProvider)));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorCareProviderDark));
    }

    @Override
    public void onVPSuccess(ArrayList<Record> records) {
        this.recordlist = records;

        getLocationPermission();

    }

    @Override
    public void onVPSuccessDetails(String title, String description, Date date) {

    }

    @Override
    public void onVPNoRecords() {

    }

    @Override
    public void onVPNoContext() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (this.markers.contains(marker)) {
            int index = this.markers.indexOf(marker);

            if (testclick.get(index) == 1) {
                new PutContext(this.recordlist.get(index)).execute();
                Intent intent = new Intent(MapActivity.this, PatientRecordViewActivity.class);
                changeActivity(intent);
            }

            else {
                for( int j = 0; j < testclick.size(); j++){
                    testclick.set(j,0);
                }
                testclick.set(index, 1);
            }

        }

        return false;
    }

}
