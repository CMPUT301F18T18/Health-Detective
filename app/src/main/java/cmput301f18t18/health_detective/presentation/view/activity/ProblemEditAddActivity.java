package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemAddEditPresenter;

public class ProblemEditAddActivity extends AppCompatActivity implements View.OnClickListener, ProblemAddEditPresenter.AddView {

    Patient patientContext;
    Problem problemContext;
    private TextView problemTitle, problemDate, problemDesc;
    ProblemAddEditPresenter problemAddEditPresenter;
    Boolean type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_edit_add);

        Intent intent = getIntent();
        this.patientContext = (Patient) intent.getSerializableExtra("PATIENT");
        problemContext = (Problem) intent.getSerializableExtra("PROBLEM");

        //true if editing problem, flase if creating new problem
        if (patientContext == null){
            type = true;
        } else {
            type = false;
        }

        problemTitle = findViewById(R.id.problemTitle);
        problemDate = findViewById(R.id.problemDate);
        problemDesc = findViewById(R.id.problemDesc);
        if (type){
            problemTitle.setText(problemContext.getTitle());
            problemDate.setText(problemContext.getStartDate().toString());
            problemDesc.setText(problemContext.getDescription());
        }
        //problemTitle.setText(problemContext.getTitle());


        problemAddEditPresenter = new ProblemAddEditPresenter(
                this,
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                ElasticSearchController.getInstance(),
                ElasticSearchController.getInstance()
        );

        Button cancelBtn = findViewById(R.id.cancelBtn);
        Button saveBtn = findViewById(R.id.saveBtn);

        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                String probTitle = problemTitle.getText().toString();
                String probDesc = problemDesc.getText().toString();
                if (type){
                    problemAddEditPresenter.editUserProblem(problemContext, probTitle, probDesc, new Date());
                }
                else {
                    problemAddEditPresenter.createNewProblem(patientContext, probTitle, probDesc, new Date());
                }
                break;
            case R.id.cancelBtn:
                finish();
                break;
        }
    }

    @Override
    public void onCreateProblem() {
        if (!type) {
            Intent problemListIntent = new Intent(this, PatientProblemsActivity.class);
            problemListIntent.putExtra("PATIENT", patientContext);
            this.startActivity(problemListIntent);
        } else {
            Toast.makeText(this, "edit record", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onEditProblem() {
        Toast.makeText(this, "edit record", Toast.LENGTH_SHORT).show();
        finish();
    }

}
