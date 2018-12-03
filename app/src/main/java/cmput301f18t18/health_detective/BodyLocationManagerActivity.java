package cmput301f18t18.health_detective;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cmput301f18t18.health_detective.presentation.view.activity.GridViewAdapter;

public class BodyLocationManagerActivity extends AppCompatActivity {
    GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location_manager);


        adapter = new GridViewAdapter(this, 1);
        GridView gridView = (GridView) findViewById(R.id.bodygrid);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(BodyLocationManagerActivity.this, "Photo Click", Toast.LENGTH_SHORT);
                toast.show();

            }
        });


    }

}



