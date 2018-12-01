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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cmput301f18t18.health_detective.DatePickerFragment;
import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.TimePickerFragment;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemAddEditPresenter;

public class ProblemEditAddActivity extends AppCompatActivity implements View.OnClickListener, ProblemAddEditPresenter.AddView, DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    Patient patientContext;
    Problem problemContext;
    private TextView problemTitle, problemDate, problemDesc;
    ProblemAddEditPresenter problemAddEditPresenter;
    Boolean type;
    Date problemDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_edit_add);

        Intent intent = getIntent();
        this.patientContext = (Patient) intent.getSerializableExtra("PATIENT");
        problemContext = (Problem) intent.getSerializableExtra("PROBLEM");

        //true if editing problem, false if creating new problem
        if (patientContext == null){
            type = true;
        } else {
            type = false;
        }

        problemTitle = findViewById(R.id.problemTitle);
        problemDate = findViewById(R.id.problemDate);
        problemDesc = findViewById(R.id.problemDesc);
        problemDate.setFocusable(false);
        if (type){
            problemTitle.setText(problemContext.getTitle());
            problemDate.setText(problemContext.getStartDate().toString());
            problemDesc.setText(problemContext.getDescription());
        }

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
                String probTitle = problemTitle.getText().toString();
                String probDesc = problemDesc.getText().toString();
                String probDate = problemDate.getText().toString();
                if (probDate == null){
                    problemDateTime = new Date();
                }
                if (type){
                    if (problemDateTime == null){
                        problemDateTime = problemContext.getStartDate();
                    }
                    problemAddEditPresenter.editUserProblem(problemContext, probTitle, probDesc, problemDateTime);
                }
                else {
                    problemAddEditPresenter.createNewProblem(patientContext, probTitle, probDesc, problemDateTime);
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
    public void onCreateProblem(Problem problem) {
            Intent problemListIntent = new Intent(this, PatientProblemsActivity.class);
            problemListIntent.putExtra("PATIENT", patientContext);
            this.startActivity(problemListIntent);

    }

    @Override
    public void onEditProblem() {
        Toast toast = Toast.makeText(this, "Problem Edited", Toast.LENGTH_SHORT);
        toast.show();
        finish();
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
