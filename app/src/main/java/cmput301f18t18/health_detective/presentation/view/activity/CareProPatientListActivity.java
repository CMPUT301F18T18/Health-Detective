package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301f18t18.health_detective.SingleAddDialog;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.PatientOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CareProPatientListPresenter;



public class CareProPatientListActivity extends AppCompatActivity implements View.OnClickListener , PatientOnClickListener, CareProPatientListPresenter.View,SingleAddDialog.AddPatientDialogListener{

    ListView listView;
    PatientListAdapter adapter;
    ArrayList<Patient> patientList = new ArrayList<>();
    CareProPatientListPresenter careProPatientListPresenter;
    CareProvider cpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_pro_patient_list);

        //This is setting the colour scheme to purple
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCareProvider)));
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorCareProviderDark));

        this.careProPatientListPresenter = new CareProPatientListPresenter(this); //creating presenter to update view

        ImageView addPatient = findViewById(R.id.addPatientBtn);
        addPatient.setOnClickListener(this);
        listView = findViewById(R.id.patientListView);
        adapter = new PatientListAdapter(this, this.patientList, this);
        listView.setAdapter(adapter);
        this.careProPatientListPresenter.getAssignedPatients(); //getting the assigned patients

    }

    @Override
    public void onResume() {
        super.onResume();
        this.careProPatientListPresenter.getAssignedPatients();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        MenuItem userIdMenu = menu.findItem(R.id.userId);
        userIdMenu.setTitle(cpContext.getUserId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent searchIntent = new Intent(this,SearchActivity.class);
                startActivity(searchIntent);
                return true;

            case R.id.Logout_option:
                careProPatientListPresenter.onLogout();
                return true;

            case R.id.userId:
                Intent userIdIntent = new Intent(this, SignUpActivity.class);
                userIdIntent.putExtra("type",1);
                startActivity(userIdIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Logout button works don't remove
        careProPatientListPresenter.onLogout();
    }


    @Override
    public void onPatientClicked(Patient patient) {
        this.careProPatientListPresenter.clickOnPatient(patient);
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
                        careProPatientListPresenter.deletePatient(patient);
                    }
                });
        AlertDialog dialog = alert.create();
        dialog.show();

    }
    private void openDialog() {
        SingleAddDialog exampleDialog = new SingleAddDialog("Add patient");
        exampleDialog.show(getSupportFragmentManager(), "Add Patient");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addPatientBtn) {
            openDialog(); // this is adding in a new patient
        }
    }

    @Override
    public void onAddPatientSuccess() {
        Toast toast = Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT);
        toast.show();
        this.careProPatientListPresenter.getAssignedPatients(); // updating this list to show the new patient
    }

    // method for when the patient is not added. Only shows a test
    @Override
    public void onAddPatientFailure() {
        Toast toast = Toast.makeText(this, "Patient Not added", Toast.LENGTH_SHORT);
        toast.show();
    }

    // when you are adding in a new patient as a care provider (method name is misleading)
    @Override
    public void applyEdit(String patient) {
        // add the patient done here
        Toast toast = Toast.makeText(this, patient, Toast.LENGTH_SHORT);
        toast.show();
        careProPatientListPresenter.addNewPatient(patient); //adding new patient to elastic search
    }


    public void onDeletePatientSuccess(Patient patient) {
        this.patientList.remove(patient); // deleting patient from list
        adapter.notifyDataSetChanged(); // showing the deletion in the list
        Toast toast = Toast.makeText(this, "Delete Patient", Toast.LENGTH_SHORT);
        toast.show();
    }

    //getting the list of patients from elastic search and updating the listview
    @Override
    public void onGetPatientSuccess(ArrayList<Patient> assignedPatients) {
        this.patientList.clear();
        this.patientList.addAll(assignedPatients);
        this.adapter.notifyDataSetChanged();
    }

    //displays an alert dialog that tells you what to click on the plus button
    // this only gets called when the data list is empty (ie: no patients)
    @Override
    public void noPatients() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true)
                .setTitle("No patients click bottom plus button to add")
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    // this method gets the current user logged in
    @Override
    public void onGetUser(CareProvider careProvider) {
        cpContext = careProvider; // sets the context to care provider
        this.careProPatientListPresenter.getAssignedPatients(); // gets assigned patients for current care provider
    }

    @Override
    public void onClickPatient() {
        Intent intent = new Intent(this, PatientProblemsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLogout() {
        Intent logoutIntent = new Intent(this,MainActivity.class);
        startActivity(logoutIntent);
    }
}
