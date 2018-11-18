package cmput301f18t18.health_detective.presentation.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

//TODO: Make the all photo section increase with each photo addition

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.MapActivity;

public class PatientRecordViewActivity extends AppCompatActivity {

    LinearLayout bodyPhotoScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);

        //stuff for all photos section
        GridViewAdapter adapter = new GridViewAdapter(this, 10);
        GridView gridView = (GridView) findViewById(R.id.allPhotosView);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(PatientRecordViewActivity.this, "Photo Click", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        // Stuff for body location photos section
        bodyPhotoScroll = (LinearLayout) findViewById(R.id.root);
        ImageView testImg = new ImageView(this);
        testImg.setImageResource(R.drawable.ic_launcher_background);

        bodyPhotoScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(PatientRecordViewActivity.this, "BL Click", Toast.LENGTH_SHORT);
            toast.show();
            }
        });
        test();
        test();
        test();
        test();
        test();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.edit_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_title:
                return true;
            case R.id.edit_date:
                return true;
            case R.id.edit_desc:
                return true;
            case R.id.edit_photo:
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }

    public void test(){
        ImageView testImg = new ImageView(this);
        testImg.setImageResource(R.drawable.ic_launcher_background);
        bodyPhotoScroll.addView(testImg);
        bodyPhotoScroll.invalidate();
    }

}
