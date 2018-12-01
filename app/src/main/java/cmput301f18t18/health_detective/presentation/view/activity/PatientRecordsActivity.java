package cmput301f18t18.health_detective.presentation.view.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cmput301f18t18.health_detective.AddDialog;
import cmput301f18t18.health_detective.DatePickerFragment;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.TimePickerFragment;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.RecordOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.RecordListPresenter;

public class PatientRecordsActivity extends AppCompatActivity implements View.OnClickListener, RecordListPresenter.View, RecordOnClickListener, AddDialog.AddDialogListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    String userId = "";
    ListView listView;
    RecordListAdapter adapter;
    ArrayList<Record> recordList = new ArrayList<>();
    RecordListPresenter recordListPresenter;
    int currentPosition;
    private String title, desc;
    private Date date;
    private Geolocation currentGeoLocation;
    private Boolean LocationPermissionsGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

        this.recordListPresenter = new RecordListPresenter(this);

        getLocationPermission();
        getDeviceLocation();

        ImageView addRecBtn = findViewById(R.id.addRecordsBtn);
        addRecBtn.setOnClickListener(PatientRecordsActivity.this);

        final Context context = PatientRecordsActivity.this;
        listView = findViewById(R.id.recordListView);


        adapter = new RecordListAdapter(this, this.recordList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recordListPresenter.onView(recordList.get(position));
            }
        });

        this.recordListPresenter.getUserRecords();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.recordListPresenter.getUserRecords();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        MenuItem userIdMenu = menu.findItem(R.id.userId);
        userIdMenu.setTitle(userId);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            case R.id.app_bar_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.Map_option:
                Intent mapIntent = new Intent(this, MapActivity.class);
                //mapIntent.putExtra("PATIENT", patientContext);
                startActivity(mapIntent);
                return true;
            case R.id.userId:
                Intent userIdIntent = new Intent(this, SignUpActivity.class);
                startActivity(userIdIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addRecordsBtn) {
            openDialog();
        }
    }

    private void openDialog() {
        AddDialog exampleDialog = new AddDialog();
        exampleDialog.show(getSupportFragmentManager(), "Add Dialog");
    }

    public void changeActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(final Record record) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true)
                .setTitle("Are you sure you want to delete?")
                .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recordListPresenter.deleteUserRecords(record);
                    }
                });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    public void onRecordView() {
        Intent intent = new Intent(PatientRecordsActivity.this, PatientRecordViewActivity.class);
        changeActivity(intent);
    }

    @Override
    public void onRecordListUpdate(ArrayList<Record> recordList) {
        this.recordList.clear();
        this.recordList.addAll(recordList);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecordDeleted(Record record) {
        this.recordList.remove(record);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateRecord() {
        Intent intent = new Intent(this, PatientRecordViewActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onRecordListUserId(String userId) {
        this.userId = userId;
        invalidateOptionsMenu();
    }

    @Override
    public void onDeleteRecordFail() {
        Toast toast = Toast.makeText(this, "Record Not Deleted", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void applyEdit(String newTitle, String newComment) {
        this.title = newTitle;
        this.desc = newComment;
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = c.getTime();
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        date = c.getTime();
        Log.d("abcdefgh", date.toString());
        recordListPresenter.createUserRecord(this.title, this.desc, this.date, null);

    }



    private void getDeviceLocation(){
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(LocationPermissionsGranted){
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            //LatLng currentLatLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                            currentGeoLocation = new Geolocation(currentLocation.getLongitude(),currentLocation.getLatitude());
                            Log.d("abcdefg",Double.toString(currentGeoLocation.getlatitude()));

                        }
                    }
                });
            }
        }catch(SecurityException ignored){

        }
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1234);
            }
        }else{ActivityCompat.requestPermissions(this,permissions,1234);
        }

    }

}
