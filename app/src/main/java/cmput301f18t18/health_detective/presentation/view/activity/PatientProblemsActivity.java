package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.MapActivity;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemsListPresenter;

public class PatientProblemsActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ProblemListAdapter adapter;
    ArrayList<Problem> problemList = new ArrayList<>();
    ProblemsListPresenter problemsListPresenter;
    Patient patientContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problems);

        Intent intent = this.getIntent();
        this.patientContext = (Patient) intent.getSerializableExtra("PATIENT");

        //Testing
        Problem problem1 = new Problem(
                "Problem 1",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc posuere nisl blandit mi bibendum porta. Etiam laoreet enim libero, at gravida enim aliquet in. Pellentesque efficitur id orci at accumsan. Donec fringilla sem vitae lacinia tincidunt. Etiam nec lectus sed lorem interdum ultrices. Vivamus euismod cursus dapibus. In quis pulvinar lorem. Nullam facilisis orci sit amet lorem suscipit, a laoreet lorem vehicula. Nulla quis tristique nibh. Nunc ipsum neque, imperdiet non sapien ut, varius condimentum velit. Vivamus in magna ut lectus finibus maximus eu a est. Integer fringilla ultrices elit, et tincidunt lacus laoreet ac. Fusce sit amet ligula massa. Etiam convallis faucibus turpis, vitae vehicula eros vehicula eget. "
        );

        this.patientContext.addProblem(problem1);

        ProblemRepo problems = new ProblemRepoMock();
        UserRepo userRepo = new UserRepoMock();

        userRepo.insertUser(patientContext);
        problems.insertProblem(problem1);


        this.problemsListPresenter = new ProblemsListPresenter(
                this,
                this,
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                problems,
                userRepo
        );

        ImageView addProblem = (ImageView) findViewById(R.id.addProbBtn);
        addProblem.setOnClickListener(this);


        listView = findViewById(R.id.problemListView);


        adapter = new ProblemListAdapter(this, this.problemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent problemsIntent = new Intent(PatientProblemsActivity.this, PatientRecordsActivity.class);
                changeActivity(problemsIntent);

            }
        });

        this.problemsListPresenter.getProblems(this.patientContext);
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
                changeActivity(searchIntent);
                return true;

            case R.id.Map_option:
                Intent mapIntent = new Intent(this,MapActivity.class);
                changeActivity(mapIntent);
                return true;

            case R.id.Logout_option:
                Intent logoutIntent = new Intent(this,MainActivity.class);
                changeActivity(logoutIntent);
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
            adapter.notifyDataSetChanged();
            changeActivity(problemsIntent);
        }
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }

    public void setProblemList(ArrayList<Problem> problemList) {
        this.problemList.clear();
        this.problemList.addAll(problemList);
        this.adapter.notifyDataSetChanged();
    }
}
