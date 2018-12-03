package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


/*
    Author: End Game
    Title: How implement left/ right swipe/fling on layout in android
    Source: https://stackoverflow.com/questions/32966069/how-implement-left-right-swipe-fling-on-layout-in-android

    This was taken for a finger swiper on the slideshow view
 */
public class PhotoSlideShowSwiper implements View.OnTouchListener {

    public GestureDetector gestureDetector;

    public PhotoSlideShowSwiper(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e){
            return super.onSingleTapUp(e);
        }
    }

    public void onSwipeRight(){}
    public void onSwipeLeft(){}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
