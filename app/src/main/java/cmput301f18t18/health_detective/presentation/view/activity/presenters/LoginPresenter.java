package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import cmput301f18t18.health_detective.domain.interactors.UserLoginContract;
import cmput301f18t18.health_detective.domain.model.User;

public class LoginPresenter implements UserLoginContract.Callback {

    public void tryLogin(String user){

    }
    @Override
    public void onLoginSuccess(User user) {

    }

    @Override
    public void onLoginFail() {

    }
}
