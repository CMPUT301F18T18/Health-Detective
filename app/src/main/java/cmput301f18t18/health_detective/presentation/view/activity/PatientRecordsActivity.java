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

public class PatientRecordsActivity extends AppCompatActivity implements View.OnClickListener, RecordListPresenter.View, RecordOnClickListener{

    ListView listView;
    RecordListAdapter adapter;
    ArrayList<Record> recordList = new ArrayList<>();
    RecordListPresenter recordListPresenter;
    Problem problemContext;



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

//        ProblemRepo problemRepo = new ProblemRepoMock();
//
//        //testing stuff
//       problemContext = new Problem(
//                1,
//                "Help I broke my shit",
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc posuere nisl blandit mi bibendum porta. Etiam laoreet enim libero, at gravida enim aliquet in. Pellentesque efficitur id orci at accumsan. Donec fringilla sem vitae lacinia tincidunt. Etiam nec lectus sed lorem interdum ultrices. Vivamus euismod cursus dapibus. In quis pulvinar lorem. Nullam facilisis orci sit amet lorem suscipit, a laoreet lorem vehicula. Nulla quis tristique nibh. Nunc ipsum neque, imperdiet non sapien ut, varius condimentum velit. Vivamus in magna ut lectus finibus maximus eu a est. Integer fringilla ultrices elit, et tincidunt lacus laoreet ac. Fusce sit amet ligula massa. Etiam convallis faucibus turpis, vitae vehicula eros vehicula eget. ",
//                new Date());
//
//        problemRepo.insertProblem(this.problemContext);
//
//        Record record1 = new Record(
//                1,
//                "Killer Whale",
//                "The killer whale or orca is a toothed whale belonging to the oceanic dolphin family, " +
//                        "of which it is the largest member. Killer whales have a diverse diet, although individual " +
//                        "populations often specialize in particular types of prey.",
//                new Date());
//        Record record2 = new Record(
//                2,
//                "Sperm Whale",
//                "The sperm whale or cachalot is the largest of the toothed whales and the largest toothed predator." +
//                        " It is the only living member of genus Physeter and one of three extant species in the sperm whale " +
//                        "family, along with the pygmy sperm whale and dwarf sperm whale of the genus Kogia.",
//                new Date());
//        Record record3 = new Record(
//                3,
//                "Beluga Whale",
//                "The beluga whale or white whale is an Arctic and sub-Arctic cetacean. " +
//                        "It is 1 of 2 members of the family Monodontidae, along with the narwhal, " +
//                        "and the only member of the genus Delphinapterus",
//                new Date());
//        Record record4 = new Record(
//                4,
//                "Narwhal",
//                "The narwhal, or narwhale, is a medium-sized toothed whale that possesses a " +
//                        "large \"tusk\" from a protruding canine tooth. It lives year-round in the Arctic" +
//                        " waters around Greenland, Canada, and Russia",
//                new Date());
//        Record record5 = new Record(
//                "Humpback Whale",
//                "The humpback whale is a species of baleen whale. One of the larger rorqual " +
//                        "species, adults range in length from 12–16 m and weigh around 25–30 metric tons." +
//                        " The humpback has a distinctive body shape, with long pectoral fins and a knobbly head"
//        );
//
//        RecordRepo recordRepo = new RecordRepoMock();
//
//        this.problemContext.addRecord(record1);
//        this.problemContext.addRecord(record2);
//        this.problemContext.addRecord(record3);
//        this.problemContext.addRecord(record5);
//        this.problemContext.addRecord(record4);
//
//
//        recordRepo.insertRecord(record1);
//        recordRepo.insertRecord(record2);
//        recordRepo.insertRecord(record3);
//        recordRepo.insertRecord(record4);

//        this.recordListPresenter = new RecordListPresenter(
//                this,
//                ThreadExecutorImpl.getInstance(),
//                MainThreadImpl.getInstance(),
//                problemRepo,
//                recordRepo
//        );



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
            recordListPresenter.createUserRecord(problemContext, "test", "test", new Date(), "test");
        }
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
        Intent intent = new Intent(this, PatientRecordViewActivity.class);
        intent.putExtra("PROBLEM", problemContext);
        this.startActivity(intent);
        //recordListPresenter.getUserRecords(problemContext);
    }

    @Override
    public void onCreateRecordFail() {
        Toast toast = Toast.makeText(this, "Record Not Added", Toast.LENGTH_SHORT);
        toast.show();

    }
}
