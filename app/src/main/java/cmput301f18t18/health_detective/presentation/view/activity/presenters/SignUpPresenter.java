package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.widget.Toast;

import cmput301f18t18.health_detective.domain.interactors.CreateUserProfileContract;
import cmput301f18t18.health_detective.domain.model.User;

public class SignUpPresenter implements CreateUserProfileContract.Callback {

    public void createNewUser(String userName, String userEmail, String userPhoneNum, Boolean type){
        return;
    }

    @Override
    public void onCUPSuccess(User user) {

    }

    @Override
    public void onCUPFail() {

    }
}
