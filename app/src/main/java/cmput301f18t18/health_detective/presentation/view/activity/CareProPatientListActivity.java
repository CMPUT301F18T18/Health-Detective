package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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

import cmput301f18t18.health_detective.PatientDialog;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
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
        adapter = new PatientListAdapter(this, this.patientList, this);
        listView.setAdapter(adapter);
        this.careProPatientListPresenter.getAssignedPatients();

//        if (patientList.size() == 0){
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setCancelable(true)
//                    .setTitle("Are you sure you want to delete?")
//                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//            AlertDialog dialog = alert.create();
//            dialog.show();
//
//            Log.d("abcdefg", "no patietns");
//        }

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

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
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
                startActivity(userIdIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
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
        this.careProPatientListPresenter.getAssignedPatients();
    }

    @Override
    public void onAddPatientFailure() {
        Toast toast = Toast.makeText(this, "Patient Not added", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void applyEdit(String patient) {
        // add the patient done here
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

    @Override
    public void onGetUser(CareProvider careProvider) {
        cpContext = careProvider;
        this.careProPatientListPresenter.getAssignedPatients();
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
