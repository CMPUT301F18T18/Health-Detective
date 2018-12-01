package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301f18t18.health_detective.PatientDialog;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.PatientOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CareProPatientListPresenter;



public class CareProPatientListActivity extends AppCompatActivity implements View.OnClickListener , PatientOnClickListener, CareProPatientListPresenter.View,PatientDialog.AddPatientDialogListener{

    ListView listView;
    PatientListAdapter adapter;
    ArrayList<Patient> patientList = new ArrayList<>();
    CareProPatientListPresenter careProPatientListPresenter;
    Patient patientContext;
    CareProvider cpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_pro_patient_list);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCareProvider)));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorCareProviderDark));
        //Toast.makeText(CareProPatientListActivity.this,"Bet you wished this actually deleted",Toast.LENGTH_SHORT).show();

        this.careProPatientListPresenter = new CareProPatientListPresenter(this);

        ImageView addPatient = findViewById(R.id.addPatientBtn);
        addPatient.setOnClickListener(this);
        listView = findViewById(R.id.patientListView);
        //patientList.add(new Patient("12345678", "12345678", "123455663424"));
        adapter = new PatientListAdapter(this, this.patientList, this);
        listView.setAdapter(adapter);
        //this.careProPatientListPresenter.getAssignedPatients(this.cpContext);


    }


    @Override
    public void onPatientClicked(Patient patient) {
        Intent intent = new Intent(this, PatientProblemsActivity.class);
        startActivity(intent);
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
                        careProPatientListPresenter.deletePatient(cpContext, patient);
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
        Toast toast = Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT);
        toast.show();
        this.careProPatientListPresenter.getAssignedPatients(cpContext);

    }

    @Override
    public void onAddPatientFailure() {
        Toast toast = Toast.makeText(this, "Patient Not added", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void applyEdit(String patient) {
        // add the patient done here
        Toast toast = Toast.makeText(this, patient, Toast.LENGTH_SHORT);
        toast.show();
        careProPatientListPresenter.addNewPatient(patient);
    }


    public void onDeletePatientSuccess(Patient patient) {
        this.patientList.remove(patient);
        adapter.notifyDataSetChanged();
        Toast toast = Toast.makeText(this, "Delete Patient", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onGetPatientSuccess(ArrayList<Patient> assignedPatients) {
        this.patientList.clear();
        this.patientList.addAll(assignedPatients);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void noPatients() {
        Toast toast = Toast.makeText(this, "NO PATIENTS", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onGetUser(CareProvider careProvider) {
        cpContext = careProvider;
        this.careProPatientListPresenter.getAssignedPatients(careProvider);
    }

}
