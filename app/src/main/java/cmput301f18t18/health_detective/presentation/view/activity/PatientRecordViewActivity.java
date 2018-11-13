package cmput301f18t18.health_detective.presentation.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cmput301f18t18.health_detective.R;

public class PatientRecordViewActivity extends AppCompatActivity {

    LinearLayout bodyPhotoScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);

        bodyPhotoScroll = (LinearLayout) findViewById(R.id.root);

        ImageView testImg = new ImageView(this);
        testImg.setImageResource(R.drawable.ic_launcher_background);
        test();
        test();
        test();
        test();
    }

    public void test(){
        ImageView testImg = new ImageView(this);
        testImg.setImageResource(R.drawable.ic_launcher_background);
        bodyPhotoScroll.addView(testImg);
        bodyPhotoScroll.invalidate();
    }


}
