package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cmput301f18t18.health_detective.DatePickerFragment;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.TimePickerFragment;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemAddEditPresenter;

public class ProblemEditAddActivity extends AppCompatActivity implements View.OnClickListener, ProblemAddEditPresenter.AddView, DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private TextView problemTitle, problemDate, problemDesc;
    private ProblemAddEditPresenter problemAddEditPresenter;
    private Boolean type;
    private Date problemDateTime;
    private String title = "";
    private String comment = "";
    private DateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY hh:mma");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_edit_add);

        // get type
        // true - editing
        // false - adding
        Intent intent = getIntent();
        type = (Boolean) intent.getSerializableExtra("TYPE");

        // set view id's
        problemTitle = findViewById(R.id.problemTitle);
        problemDate = findViewById(R.id.problemDate);
        problemDesc = findViewById(R.id.problemDesc);
        problemDate.setFocusable(false);
        TextView cancelBtn = findViewById(R.id.cancelBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button addDateBtn = findViewById(R.id.addDateBtn);

        // time default set
        problemDate.setText(dateFormat.format(new Date()).replace("AM","am").replace("PM","pm"));
        // create problem presenter
        problemAddEditPresenter = new ProblemAddEditPresenter(this);

        // set on click listeners for buttons
        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        addDateBtn.setOnClickListener(this);
    }

    // on back button pressed return to last activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // on click for options items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // on click for our buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // on save button click, edit or create new problem based on type
            case R.id.saveBtn:
                title = problemTitle.getText().toString();
                comment = problemDesc.getText().toString();

                if (type){
                    problemAddEditPresenter.editUserProblem(title, comment, problemDateTime);
                }
                else {
                    problemAddEditPresenter.createNewProblem(title, comment, problemDateTime);
                }
                break;
            // on cancel return go to last activity
            case R.id.cancelBtn:
                finish();
                break;
            // on add date click, open time picker fragment
            case R.id.addDateBtn:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

        }
    }

    // on successful creation change activity
    @Override
    public void onCreateProblem() {
        Toast toast = Toast.makeText(this, "Problem Created", Toast.LENGTH_SHORT);
        toast.show();
        Intent problemListIntent = new Intent(this, PatientProblemsActivity.class);
        this.startActivity(problemListIntent);
    }

    // on successful edit change activity
    @Override
    public void onEditProblem() {
        Toast toast = Toast.makeText(this, "Problem Edited", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    // get problem details, for editing problem
    @Override
    public void onProblemDetails(String title, String description, Date date) {
        if (type){
            this.title = title;
            this.comment = description;
            this.problemDateTime = date;
            if (problemDateTime != null) {
                this.problemDateTime = new Date();
            }
            problemTitle.setText(title);
            problemDesc.setText(comment);
            // set date text using our date format
            problemDate.setText(dateFormat.format(problemDateTime).replace("AM","am").replace("PM","pm"));
        }
    }

    // after successful date selection method is called, updates date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        problemDateTime = c.getTime();
        // call timepicker after date has been selected
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    // after successful time selection method is called, updates time
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(problemDateTime);
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        problemDateTime = c.getTime();
        problemDate.setText(dateFormat.format(problemDateTime).replace("AM","am").replace("PM","pm"));
    }
}
