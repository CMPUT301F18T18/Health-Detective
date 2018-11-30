package cmput301f18t18.health_detective.presentation.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Photo;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        ImageView currentImg = findViewById(R.id.currentImg);
        ImageView deleteBtn = findViewById(R.id.deleteImg);
        ImageView editBtn = findViewById(R.id.editImg);
        currentImg.setImageResource(R.drawable.ic_launcher_background);

        currentImg.setOnTouchListener(new PhotoSlideShowSwiper(this){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Toast.makeText(PhotoViewActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                currentImg.setImageResource(R.drawable.ic_launcher_background);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                Toast.makeText(PhotoViewActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                currentImg.setImageResource(R.drawable.baseline_account_circle_24);
            }
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

    }


}
