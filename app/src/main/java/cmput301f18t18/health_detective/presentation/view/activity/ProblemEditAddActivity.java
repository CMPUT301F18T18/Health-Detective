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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_edit_add);

        Intent intent = getIntent();
        type = (Boolean) intent.getSerializableExtra("TYPE");

        problemTitle = findViewById(R.id.problemTitle);
        problemDate = findViewById(R.id.problemDate);
        problemDesc = findViewById(R.id.problemDesc);
        problemDate.setFocusable(false);



        problemAddEditPresenter = new ProblemAddEditPresenter(this);

        TextView cancelBtn = findViewById(R.id.cancelBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button addDateBtn = findViewById(R.id.addDateBtn);

        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        addDateBtn.setOnClickListener(this);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.addDateBtn:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

        }
    }

    @Override
    public void onCreateProblem() {
        Intent problemListIntent = new Intent(this, PatientProblemsActivity.class);
        this.startActivity(problemListIntent);
    }

    @Override
    public void onEditProblem() {
        Toast toast = Toast.makeText(this, "Problem Edited", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    @Override
    public void onProblemDetails(String title, String description, Date date) {
        if (type){
            this.title = title;
            this.comment = description;
            this.problemDateTime = date;

            if (problemDateTime != null) {

            }

            problemTitle.setText(title);
            problemDesc.setText(comment);
            problemDate.setText(problemDateTime.toString());
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date currentDate = c.getTime();
        problemDateTime = currentDate;
        problemDate.setText((CharSequence) currentDate.toString());
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date theSameDate = null;
        String date = problemDate.getText().toString();
        try {
            theSameDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(theSameDate);
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        Date currentDate = c.getTime();
        problemDateTime = currentDate;
        problemDate.setText((CharSequence) currentDate.toString());
    }
}
