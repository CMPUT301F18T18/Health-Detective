package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cmput301f18t18.health_detective.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    // Drop down checkbox adapter
    private final List<BodyPartCheckBoxAdapter.SpinnerItem<String>> spinnerItems = new ArrayList<>();
    private final Set<String> selectedBodyPart = new HashSet<>();
    List<String> bodyParts = new ArrayList<>(Arrays.asList("Left Leg", "Right Leg", "Left Arm", "Right Arm", "Head"));
    EditText keywordText;

    //searchList adapter
    ListView listView;
    SearchListAdapter searchAdapter;
    ArrayList<String> testList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keywordText = findViewById(R.id.keywordText);
        //keywordText.setOnClickListener(this);

        TextView keywordBtn = findViewById(R.id.keywordBtn);
        keywordBtn.setOnClickListener(this);

        TextView geoBtn = findViewById(R.id.geoLocationBtn);
        geoBtn.setOnClickListener(this);

        // fill the 'spinner_items' array with all items to show
        //List<String> all_objects = new ArrayList<>(Arrays.asList("Left Leg", "Right Leg", "Left Arm", "Right Arm", "Head"));
        for(String o : bodyParts) {
            spinnerItems.add(new BodyPartCheckBoxAdapter.SpinnerItem<>(o, o));
        }

        String headerText = "Body Part";
        Spinner spinner = findViewById(R.id.spinner);
        BodyPartCheckBoxAdapter checkAdapter = new BodyPartCheckBoxAdapter(this, headerText, spinnerItems, selectedBodyPart);
        spinner.setAdapter(checkAdapter);

        // Search List stuff
        listView = findViewById(R.id.searchList);
        testList.add("test");
        testList.add("test2");
        testList.add("test2");
        testList.add("test2");
        testList.add("test2");
        searchAdapter = new SearchListAdapter(this, testList);
        listView.setAdapter(searchAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast toast = Toast.makeText(SearchActivity.this, "Task Click", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.keywordBtn:
                String searchKeyword = keywordText.getText().toString();
                getBodyParts();
                //Toast toast = Toast.makeText(SearchActivity.this, searchKeyword, Toast.LENGTH_SHORT);
                //toast.show();
                break;
            case R.id.geoLocationBtn:
                Toast toast = Toast.makeText(SearchActivity.this, "Geo Click", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

    }

    // Seeing what the user picked for body parts
    public void getBodyParts(){
        List<String> testList = new ArrayList<>(selectedBodyPart);
        for(int x=0; x<testList.size(); ++x) {
            Toast toast = Toast.makeText(SearchActivity.this, testList.get(x), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
