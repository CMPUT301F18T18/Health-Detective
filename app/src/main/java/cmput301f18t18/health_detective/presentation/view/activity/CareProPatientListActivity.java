package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301f18t18.health_detective.PatientDialog;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.PatientOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CareProPatientListPresenter;


public class CareProPatientListActivity extends AppCompatActivity implements View.OnClickListener , PatientOnClickListener, CareProPatientListPresenter.View, PatientDialog.AddPatientDialogListener{

    ListView listView;
    PatientListAdapter adapter;
    ArrayList<Patient> patientList = new ArrayList<>();
    CareProPatientListPresenter careProPatientListPresenter;
    Patient patientContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_pro_patient_list);

        this.careProPatientListPresenter = new CareProPatientListPresenter(this);

        ImageView addPatient = findViewById(R.id.addPatientBtn);
        addPatient.setOnClickListener(this);
        listView = findViewById(R.id.patientListView);
        adapter = new PatientListAdapter(this, this.patientList, this);
        listView.setAdapter(adapter);

    }


    @Override
    public void onPatientClicked(Patient patient) {

    }

    @Override
    public void onDeleteClicked(Patient patient) {
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
                        Toast.makeText(CareProPatientListActivity.this,"Bet you wished this actually deleted",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = alert.create();
        dialog.show();


    }
    private void openDialog() {
        PatientDialog exampleDialog = new PatientDialog();
        exampleDialog.show(getSupportFragmentManager(), "Add Patient");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addPatientBtn) {
            openDialog();
        }
    }

    @Override
    public void onAddPatientSuccess() {
        //Intent intent = new Intent(this, PatientProblemsActivity.class);
        //startActivity(intent);
        Toast toast = Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void applyEdit(String patient) {
        // add the patient done here
    }
}
