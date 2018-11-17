package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

public abstract class BaseDialogFragment<T> extends DialogFragment{

    private T activityContext;

    public final T getActivityContext(){
        return activityContext;
    }

    @Override
    public void onAttach(Activity activity){
        activityContext = (T) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityContext = null;
    }

}
