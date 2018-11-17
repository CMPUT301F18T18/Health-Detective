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

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.MapActivity;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemsListPresenter;

public class PatientProblemsActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ProblemListAdapter adapter;
    ArrayList<String> testList = new ArrayList<>();
    ProblemsListPresenter problemsListPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problems);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);
        if (problemsListPresenter == null){
            problemsListPresenter = new ProblemsListPresenter();
        }

        // commented out as just adding the parent activity enables back arrow
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView addProblem = (ImageView)findViewById(R.id.addProbBtn);
        addProblem.setOnClickListener(this);


        listView = findViewById(R.id.problemListView);
        testList.add("test");
        testList.add("test2");

        adapter = new ProblemListAdapter(this, testList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent problemsIntent = new Intent(PatientProblemsActivity.this, PatientRecordsActivity.class);
                changeActivity(problemsIntent);

            }
        });


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
            testList.add("test3");
            adapter.notifyDataSetChanged();
            changeActivity(problemsIntent);
        }
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }
}
