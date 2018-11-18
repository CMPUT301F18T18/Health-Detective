package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.RecordListPresenter;

public class PatientRecordsActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    RecordListAdapter adapter;
    ArrayList<Record> recordList = new ArrayList<>();
    RecordListPresenter recordListPresenter;
    Problem problemContext;


//    TextView recTitleView = findViewById(R.id.recordTitle);
//    TextView recUserView = findViewById(R.id.recordUser);
//    TextView recDescView = findViewById(R.id.recordDesc);
//    TextView recBLView = findViewById(R.id.recordBL);
//    TextView recDateView = findViewById(R.id.recordDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

        Intent newIntent = this.getIntent();
        this.problemContext = (Problem) newIntent.getSerializableExtra("PROBLEM");

        //testing stuff
        ProblemRepo problemRepo = new ProblemRepoMock();
        problemContext = new Problem();
        problemRepo.insertProblem(this.problemContext);

        adapter = new RecordListAdapter(this, recordList);

        recordListPresenter = new RecordListPresenter(
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                problemRepo,
                new RecordRepoMock(),
                adapter,
                PatientRecordsActivity.this
        );

        Button addRecBtn = findViewById(R.id.addRecordBtn);
        addRecBtn.setOnClickListener(PatientRecordsActivity.this);

        final Context context = PatientRecordsActivity.this;
        listView = findViewById(R.id.recordListView);
        //recordList.add("test");
        //recordList.add(new Record());

        recordListPresenter.getUserRecords(problemContext);

        //adapter = new RecordListAdapter(this, recordList);
        adapter = recordListPresenter.getAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(context, "Record Click", Toast.LENGTH_SHORT);
                toast.show();
                Intent problemsIntent = new Intent(PatientRecordsActivity.this, PatientRecordViewActivity.class);
                changeActivity(problemsIntent);

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addRecordBtn){
            recordListPresenter.createUserRecord(this, problemContext, "test", "test", new Date());
            adapter.notifyDataSetChanged();
        }
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }
}
