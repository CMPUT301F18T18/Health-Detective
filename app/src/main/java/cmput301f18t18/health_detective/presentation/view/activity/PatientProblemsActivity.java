package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.ProblemOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemsListPresenter;

public class PatientProblemsActivity extends AppCompatActivity implements View.OnClickListener, ProblemsListPresenter.View, ProblemOnClickListener {
    String userId = "";
    ListView listView;
    ProblemListAdapter adapter;
    ArrayList<Problem> problemList = new ArrayList<>();
    ProblemsListPresenter problemsListPresenter;
    ImageView addProblem;
    Boolean userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problems);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //gets rid of back arrow on top bar if patient
        problemsListPresenter = new ProblemsListPresenter(this);

        addProblem = findViewById(R.id.addProbBtn);
        addProblem.setOnClickListener(this);
    }

    //This method gets called when you go back to this activity
    // It gets all the current problems
    @Override
    public void onResume() {
        super.onResume();
        this.problemsListPresenter.getProblems();
    }

    @Override
    public void onBackPressed() {
        // Logout button works don't remove
        problemsListPresenter.onLogout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        MenuItem userIdMenu = menu.findItem(R.id.userId);
        userIdMenu.setTitle(userId);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent careprovider = new Intent(this, CareProPatientListActivity.class);
                startActivity(careprovider);
                return true;

            case R.id.Logout_option:
                problemsListPresenter.onLogout();
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
    public void onClick(View v) {
        if (v.getId() == R.id.addProbBtn){
            // this is when you click add a problem
            Intent problemsIntent = new Intent(this,ProblemEditAddActivity.class);
            /*
            The same acitivy is used for creating and editing a problem. TYPE tells that activity
            if you are adding a new problem or editing an existing one
             */
            problemsIntent.putExtra("TYPE", (Boolean) false);
            startActivity(problemsIntent);
        }
    }

    @Override
    public void onProblemListUpdate(ArrayList<Problem> problemList) {
        this.problemList.clear();
        this.problemList.addAll(problemList);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onProblemListUserId(String userId) {
        if(!userType) {
            this.userId = userId;
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onProblemDeleted(Problem problem) {
        this.problemList.remove(problem);
        this.adapter.notifyDataSetChanged();
    }

    //This gets called when you click on a user problem to view the records
    @Override
    public void onViewProblem() {
        Intent recordsIntent = new Intent(this, PatientRecordsActivity.class);
        startActivity(recordsIntent);
    }

    // this gets called when you are editing a problem
    @Override
    public void onEditProblem() {
        Intent problemsIntent = new Intent(this,ProblemEditAddActivity.class);
        problemsIntent.putExtra("TYPE", (Boolean) true);
        startActivity(problemsIntent);
    }

    @Override
    public void onLogout() {
        Intent logoutIntent = new Intent(this,MainActivity.class);
        startActivity(logoutIntent);
    }

    //This is a confirmation dialog box when the user clicks delete
    @Override
    public void onDeleteClicked(final Problem problem) {
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
                        problemsListPresenter.deleteProblem(problem);
                    }
                });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    // This edits the problem chosen and puts it into elastic search
    @Override
    public void onEditClicked(Problem problem) {
        problemsListPresenter.onEdit(problem);
    }

    // updates the presenter to tell them you are moving to the record section
    @Override
    public void onRecordsClicked(Problem problem) {
        problemsListPresenter.onView(problem);
    }

    //This function only gets returned if the current user is a patient
    @Override
    public void getPatientUser(Patient patient) {
        userType = false;
        init();
    }

    //This function only gets returned if the current user is a care provider
    // Changes the color scheme to be purple
    @Override
    public void getCPUser(CareProvider careProvider) {
        userType = true;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.userId = careProvider.getUserId();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorCareProviderDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCareProvider)));
        addProblem.setImageResource(R.drawable.cp_circle);
        init();
    }

    @Override
    public void noProblems() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true)
                .setTitle("No problems click bottom plus button to add")
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void init(){
        if (userType){
            addProblem.setVisibility(View.GONE); // if care provider, set the add problem button to be invisible
        }
        //creating adapter for a problem list
        adapter = new ProblemListAdapter(this, this.problemList, this, userType);
        listView = findViewById(R.id.problemListView);
        listView.setAdapter(adapter); // setting the problem list

        this.problemsListPresenter.getProblems(); //getting all the problems associated with the patient
    }


}
