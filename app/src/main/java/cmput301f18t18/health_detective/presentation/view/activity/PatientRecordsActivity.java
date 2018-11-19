package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.AddDialog;
import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.RecordOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.RecordListPresenter;

public class PatientRecordsActivity extends AppCompatActivity implements View.OnClickListener, RecordListPresenter.View, RecordOnClickListener, AddDialog.AddDialogListener{

    ListView listView;
    RecordListAdapter adapter;
    ArrayList<Record> recordList = new ArrayList<>();
    RecordListPresenter recordListPresenter;
    Problem problemContext;
    int currentPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

        Intent newIntent = this.getIntent();
        this.problemContext = (Problem) newIntent.getSerializableExtra("PROBLEM");

        this.recordListPresenter = new RecordListPresenter(
                this,
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                ElasticSearchController.getInstance(),
                ElasticSearchController.getInstance()
        );


        adapter = new RecordListAdapter(this, recordList, this);


        ImageView addRecBtn = findViewById(R.id.addRecordsBtn);
        addRecBtn.setOnClickListener(PatientRecordsActivity.this);

        final Context context = PatientRecordsActivity.this;
        listView = findViewById(R.id.recordListView);


        adapter = new RecordListAdapter(this, this.recordList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                Intent intent = new Intent(PatientRecordsActivity.this, PatientRecordViewActivity.class);
                intent.putExtra("RECORD", recordList.get(position));
                changeActivity(intent);
            }
        });

        this.recordListPresenter.getUserRecords(this.problemContext);

    }

    @Override
    public void onResume() {
        super.onResume();
        this.recordListPresenter.getUserRecords(this.problemContext);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addRecordsBtn){
            openDialog();
        }
    }
    private void openDialog(){
        AddDialog exampleDialog = new AddDialog();
        exampleDialog.show(getSupportFragmentManager(), "Add Dialog");
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(Record record) {

        this.recordListPresenter.deleteUserRecords(problemContext, record);
    }

    @Override
    public void onRecordListUpdate(ArrayList<Record> recordList) {
        this.recordList.clear();
        this.recordList.addAll(recordList);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecordDeleted(Record record) {
        this.recordList.remove(record);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateRecord() {
        Intent intent = new Intent(PatientRecordsActivity.this, PatientRecordViewActivity.class);
        intent.putExtra("RECORD", recordList.get(currentPosition));
        changeActivity(intent);
    }

    @Override
    public void onCreateRecordFail() {
        Toast toast = Toast.makeText(this, "Record Not Added", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onDeleteRecordFail() {
        Toast toast = Toast.makeText(this, "Record Not Deleted", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void applyEdit(String title, String comment) {
        recordListPresenter.createUserRecord(problemContext, title, comment, new Date(), "test");
    }
}
