package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.PatientOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CareProPatientListPresenter;


public class CareProPatientListActivity extends AppCompatActivity implements View.OnClickListener , PatientOnClickListener, CareProPatientListPresenter.View{

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

    }

    @Override
    public void onClick(android.view.View v) {

    }

    @Override
    public void onAddPatientSuccess() {
        //Intent intent = new Intent(this, PatientProblemsActivity.class);
        //startActivity(intent);
        Toast toast = Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT);
        toast.show();
    }
}
