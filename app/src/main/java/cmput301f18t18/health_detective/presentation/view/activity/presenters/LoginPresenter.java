package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

public class LoginPresenter implements UserLogin.Callback {

    String userId;

    public void tryLogin(String userId){

    }

    @Override
    public void onLoginPatientSuccess(Patient patient) {

    }

    @Override
    public void onLoginCareProviderSuccess(CareProvider careProvider) {

    }

    @Override
    public void onLoginFail() {

    }
}
