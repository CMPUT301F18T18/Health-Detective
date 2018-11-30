package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//TODO: Make the all photo section increase with each photo addition

import java.util.Calendar;
import java.util.Date;

import cmput301f18t18.health_detective.DatePickerFragment;
import cmput301f18t18.health_detective.EditDialog;
import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.TimePickerFragment;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.RecordViewPresenter;

public class PatientRecordViewActivity extends AppCompatActivity implements View.OnClickListener, RecordViewPresenter.View, EditDialog.ExampleDialogListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    LinearLayout bodyPhotoScroll;
    Record record;
    RecordViewPresenter recordViewPresenter;
    TextView recordTitle, recordDate, recordDesc, backBLTag, frontBLTag;
    ImageView frontBL, backBL, addNPhoto;
    Patient patientContext;
    byte[] image;
    int testImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);

        Intent newIntent = this.getIntent();
        this.record = (Record) newIntent.getSerializableExtra("RECORD");
        this.patientContext = (Patient) newIntent.getSerializableExtra("USER");
        this.image = (byte[]) newIntent.getSerializableExtra("PHOTO");
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

        addNPhoto.setOnClickListener(this);
        frontBL.setOnClickListener(this);
        backBL.setOnClickListener(this);


        setTextViews();
        RecordRepoMock mockRecord = new RecordRepoMock();
        mockRecord.insertRecord(new Record("test", "test", new Date()));


//        recordViewPresenter = new RecordViewPresenter(
//                this,
//                ThreadExecutorImpl.getInstance(),
//                MainThreadImpl.getInstance(),
//                ElasticSearchController.getInstance()
//                //mockRecord
//        );
        recordViewPresenter = new RecordViewPresenter(this);

        //stuff for all photos section
//        GridViewAdapter adapter = new GridViewAdapter(this, 1);
//        GridView gridView = (GridView) findViewById(R.id.allPhotosView);
//
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast toast = Toast.makeText(PatientRecordViewActivity.this, "Photo Click", Toast.LENGTH_SHORT);
//                toast.show();
//                Intent intent = new Intent(PatientRecordViewActivity.this, PhotoViewActivity.class);
//                startActivity(intent);
//            }
//        });


        // Stuff for body location photos section
        bodyPhotoScroll = (LinearLayout) findViewById(R.id.root);
        ImageView testImg = new ImageView(this);
        testImg.setImageResource(R.drawable.ic_launcher_background);
        setAllPhotoScroll();

        bodyPhotoScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(PatientRecordViewActivity.this, "BL Click", Toast.LENGTH_SHORT);
                Intent intent = new Intent(PatientRecordViewActivity.this, PhotoViewActivity.class);
                startActivity(intent);
            toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        MenuItem userIdMenu = menu.findItem(R.id.userId);
        userIdMenu.setTitle(patientContext.getUserId());
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                openDialog(promptTitle,0,record.getTitle());
                return true;
            case R.id.edit_date:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
                return true;
            case R.id.edit_desc:
                String promptDesc = "Edit Description";
                openDialog(promptDesc,2,record.getComment());
                return true;
            case R.id.edit_photo:
                Intent camaraIntent = new Intent(this, CamaraActivity.class);
                startActivity(camaraIntent);
                return true;
            case R.id.userId:
                Intent userIdIntent = new Intent(this, SignUpActivity.class);
                userIdIntent.putExtra("PATIENT", patientContext);
                startActivity(userIdIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void setAllPhotoScroll(){
        ImageView testImg = new ImageView(this);
        testImg.setImageResource(R.drawable.ic_launcher_background);
        bodyPhotoScroll.addView(testImg);
        bodyPhotoScroll.invalidate();
    }


    @Override
    public void onEditRecord(Record record) {
        setTextViews();
        Toast toast = Toast.makeText(PatientRecordViewActivity.this, "Record is edited", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setTextViews(){
        recordTitle.setText(record.getTitle());
        recordDate.setText(record.getDate().toString());
        recordDesc.setText(record.getComment());
    }

    private void openDialog(String prompt,int type,String recordInfo){
        EditDialog exampleDialog = new EditDialog(prompt,type,recordInfo);
        exampleDialog.show(getSupportFragmentManager(), "Edit Dialog");

    }

    @Override
    public void applyEditTitle(String editedText) {
        recordViewPresenter.editUserRecord(record, editedText, record.getComment(), record.getDate(),record.getGeolocation());
    }

    @Override
    public void applyEditDesc(String editedComment) {
        recordViewPresenter.editUserRecord(record, record.getTitle(), editedComment, record.getDate(),record.getGeolocation());
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date currentDate = c.getTime();
        recordViewPresenter.editUserRecord(record, record.getTitle(), record.getComment(),currentDate ,record.getGeolocation());

        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date ourDate = record.getDate();
        Calendar c = Calendar.getInstance();
        c.setTime(ourDate);
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        Date currentDate = c.getTime();
        recordViewPresenter.editUserRecord(record,record.getTitle(),record.getComment(), currentDate,record.getGeolocation());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNPhoto:
                // go to camera activity, but make sure that it gets rid of add body location selection
                this.onBackPressed();
            case R.id.frontBL:
                Intent camaraIntent = new Intent(this, CamaraActivity.class);
                camaraIntent.putExtra("USER", patientContext);
                camaraIntent.putExtra("RECORD", record);
                startActivity(camaraIntent);
                //dialog box for add new front body location photo
            case R.id.backBL:
                Intent backBlIntent = new Intent(this, CamaraActivity.class);
                backBlIntent.putExtra("USER", patientContext);
                backBlIntent.putExtra("RECORD", record);
                startActivity(backBlIntent);
                //dialog box for add new back body location photo
        }
    }
}
