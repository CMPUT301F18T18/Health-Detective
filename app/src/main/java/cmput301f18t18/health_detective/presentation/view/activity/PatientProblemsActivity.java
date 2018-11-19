package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.ProblemOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemsListPresenter;

public class PatientProblemsActivity extends AppCompatActivity implements View.OnClickListener,
                                                                          ProblemsListPresenter.View, ProblemOnClickListener {

    ListView listView;
    ProblemListAdapter adapter;
    ArrayList<Problem> problemList = new ArrayList<>();
    ProblemsListPresenter problemsListPresenter;
    Patient patientContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problems);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = this.getIntent();
        this.patientContext = (Patient) intent.getSerializableExtra("PATIENT");

        this.problemsListPresenter = new ProblemsListPresenter(
                this,
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                ElasticSearchController.getInstance(),
                ElasticSearchController.getInstance()
        );

        ImageView addProblem = findViewById(R.id.addProbBtn);
        addProblem.setOnClickListener(this);


        listView = findViewById(R.id.problemListView);


        adapter = new ProblemListAdapter(this, this.problemList, this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        this.problemsListPresenter.getProblems(this.patientContext);
    }

    @Override
    public void onBackPressed() {
        // Logout button works don't remove
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent searchIntent = new Intent(this,SearchActivity.class);
                startActivity(searchIntent);
                return true;

            case R.id.Map_option:
                Intent mapIntent = new Intent(this,MapActivity.class);
                startActivity(mapIntent);
                return true;

            case R.id.Logout_option:
                Intent logoutIntent = new Intent(this,MainActivity.class);
                startActivity(logoutIntent);
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
            problemsIntent.putExtra("PATIENT", this.patientContext);
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
    public void onProblemDeleted(Problem problem) {
        this.problemList.remove(problem);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteClicked(Problem problem) {
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
                        problemsListPresenter.deleteProblem(patientContext, problem);
                    }
                });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    @Override
    public void onEditClicked(Problem problem) {
        Intent problemsIntent = new Intent(this,ProblemEditAddActivity.class);
        problemsIntent.putExtra("PROBLEM", problem);
        startActivity(problemsIntent);
    }

    @Override
    public void onRecordsClicked(Problem problem) {
        Intent recordsIntent = new Intent(this, PatientRecordsActivity.class);
        recordsIntent.putExtra("PROBLEM", problem);
        startActivity(recordsIntent);
    }

}
