package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemAddEditPresenter;

public class ProblemEditAddActivity extends AppCompatActivity implements ProblemAddEditPresenter.AddView {

    Patient patientContext;
    Problem problemContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_edit_add);

        Intent intent = getIntent();
        this.patientContext = (Patient) intent.getSerializableExtra("PATIENT");

    }
}
