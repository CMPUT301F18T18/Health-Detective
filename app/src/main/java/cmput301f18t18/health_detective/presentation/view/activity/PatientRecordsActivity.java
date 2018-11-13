package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cmput301f18t18.health_detective.R;

public class PatientRecordsActivity extends AppCompatActivity {

    ListView listView;
    RecordListAdapter adapter;
    ArrayList<String> testList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

        listView = findViewById(R.id.recordListView);
        testList.add("test");
        testList.add("test2");

        adapter = new RecordListAdapter(this, testList);
        listView.setAdapter(adapter);

    }

}
