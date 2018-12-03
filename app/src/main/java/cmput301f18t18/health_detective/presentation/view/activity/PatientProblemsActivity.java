package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;
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


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        problemsListPresenter = new ProblemsListPresenter(this);

        addProblem = findViewById(R.id.addProbBtn);
        addProblem.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.problemsListPresenter.getProblems();
    }

    @Override
    public void onBackPressed() {
        // Logout button works don't remove
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app

        getMenuInflater().inflate(R.menu.logout_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
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
    public void onClick(View v) {
        if (v.getId() == R.id.addProbBtn){
            Intent problemsIntent = new Intent(this,ProblemEditAddActivity.class);
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


    @Override
    public void onViewProblem() {
        Intent recordsIntent = new Intent(this, PatientRecordsActivity.class);
        startActivity(recordsIntent);
    }

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

    @Override
    public void onEditClicked(Problem problem) {
        problemsListPresenter.onEdit(problem);
    }

    @Override
    public void onRecordsClicked(Problem problem) {
        problemsListPresenter.onView(problem);
    }


    @Override
    public void getPatientUser(Patient patient) {
        userType = false;
        init();
    }

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
            addProblem.setVisibility(View.GONE);
        }
        adapter = new ProblemListAdapter(this, this.problemList, this, userType);
        listView = findViewById(R.id.problemListView);
        listView.setAdapter(adapter);

        this.problemsListPresenter.getProblems();
    }


}
