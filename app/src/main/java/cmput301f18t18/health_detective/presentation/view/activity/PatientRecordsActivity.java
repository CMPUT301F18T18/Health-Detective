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

import cmput301f18t18.health_detective.R;

public class PatientRecordsActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    RecordListAdapter adapter;
    ArrayList<String> testList = new ArrayList<>();


//    TextView recTitleView = findViewById(R.id.recordTitle);
//    TextView recUserView = findViewById(R.id.recordUser);
//    TextView recDescView = findViewById(R.id.recordDesc);
//    TextView recBLView = findViewById(R.id.recordBL);
//    TextView recDateView = findViewById(R.id.recordDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

        ImageView addRecordBtn = findViewById(R.id.addRecordBtn);
        addRecordBtn.setOnClickListener(this);

        final Context context = PatientRecordsActivity.this;
        listView = findViewById(R.id.recordListView);
        testList.add("test");
        testList.add("test2");


        adapter = new RecordListAdapter(this, testList);
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

        }
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }
}
