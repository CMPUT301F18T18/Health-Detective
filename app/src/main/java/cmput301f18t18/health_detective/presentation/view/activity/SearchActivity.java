package cmput301f18t18.health_detective.presentation.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cmput301f18t18.health_detective.R;

public class SearchActivity extends AppCompatActivity {

    private final List<BodyPartCheckBoxAdapter.SpinnerItem<String>> spinner_items = new ArrayList<>();
    private final Set<String> selected_items = new HashSet<>();
    List<String> bodyParts = new ArrayList<>(Arrays.asList("Left Leg", "Right Leg", "Left Arm", "Right Arm", "Head"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // fill the 'spinner_items' array with all items to show
        //List<String> all_objects = new ArrayList<>(Arrays.asList("Left Leg", "Right Leg", "Left Arm", "Right Arm", "Head"));
        //all_objects.add("test");// from wherever
        for(String o : bodyParts) {
            spinner_items.add(new BodyPartCheckBoxAdapter.SpinnerItem<>(o, o));
        }

        // to start with any pre-selected, add them to the `selected_items` set

        String headerText = "Body Part";

        Spinner spinner = findViewById(R.id.spinner);
        BodyPartCheckBoxAdapter adapter = new BodyPartCheckBoxAdapter(this, headerText, spinner_items, selected_items);
        spinner.setAdapter(adapter);

        // when you want to see what the user has selected, look in the `selected_items`
        // set anywhere in your activity
    }
}
