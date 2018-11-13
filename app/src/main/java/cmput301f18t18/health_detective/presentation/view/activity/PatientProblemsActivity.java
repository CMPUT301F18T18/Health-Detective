package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.R;

public class PatientProblemsActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ProblemListAdapter adapter;
    ArrayList<String> testList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problems);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);



        Button addProblem = (Button)findViewById(R.id.addProbBtn);
        addProblem.setOnClickListener(this);


        listView = findViewById(R.id.problemListView);
        testList.add("test");
        testList.add("test2");

        adapter = new ProblemListAdapter(this, testList);
        listView.setAdapter(adapter);


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
