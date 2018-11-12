package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import cmput301f18t18.health_detective.R;

public class PatientProblemsActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problems);



        Button addProblem = (Button)findViewById(R.id.addProbBtn);
        addProblem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addProbBtn){
            Intent problemsIntent = new Intent(this,ProblemEditAddActivity.class);
            changeActivity(problemsIntent);

        }
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }
}
