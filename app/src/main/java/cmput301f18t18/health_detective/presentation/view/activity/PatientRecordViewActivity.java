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
import android.widget.TextView;
import android.widget.Toast;

//TODO: Make the all photo section increase with each photo addition

import java.util.Date;

import cmput301f18t18.health_detective.ExampleDialog;
import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.MapActivity;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.RecordViewPresenter;

public class PatientRecordViewActivity extends AppCompatActivity implements RecordViewPresenter.View{

    LinearLayout bodyPhotoScroll;
    Record record;
    RecordViewPresenter recordViewPresenter;
    TextView recordTitle, recordDate, recordDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);

        Intent newIntent = this.getIntent();
        this.record = (Record) newIntent.getSerializableExtra("RECORD");

        recordTitle = findViewById(R.id.recTitle);
        recordDate = findViewById(R.id.recordDate);
        recordDesc = findViewById(R.id.commentView);
        setTextViews();

        recordViewPresenter = new RecordViewPresenter(
                this,
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                ElasticSearchController.getInstance()
        );





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
            case R.id.edit_title:
                openDialog();
                recordViewPresenter.editUserRecord(record, "Whale Test", "whales are great", new Date());
                return true;
            case R.id.edit_date:
                openDialog();
                recordViewPresenter.editUserRecord(record, "Whale Test", "whales are great", new Date());
                return true;
            case R.id.edit_desc:
                openDialog();
                recordViewPresenter.editUserRecord(record, "Whale Test", "whales are great", new Date());
                return true;
            case R.id.edit_photo:
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void test(){
        ImageView testImg = new ImageView(this);
        testImg.setImageResource(R.drawable.ic_launcher_background);
        bodyPhotoScroll.addView(testImg);
        bodyPhotoScroll.invalidate();
    }

    @Override
    public void onEditRecord(Record record) {
        setTextViews();
        Toast toast = Toast.makeText(PatientRecordViewActivity.this, "Record is edited", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setTextViews(){
        recordTitle.setText(record.getTitle());
        recordDate.setText(record.getDate().toString());
        recordDesc.setText(record.getComment());
    }

    private void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "Edit Dialog");

    }
}
