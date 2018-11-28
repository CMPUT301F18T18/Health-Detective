package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.interactors.impl.UserLoginImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;

public class LoginPresenter implements UserLogin.Callback {

    private View view;

    public interface View {
        void onLoginPatient(Patient patient);
        void onLoginCareProvider(CareProvider careProvider);
        void onInvalidUserId();
        void onUserDoesNotExist();
    }

    public LoginPresenter(View view)
    {
        this.view = view;
    }

    /**
     * Method that calls on interactor that will try to login in the user
     * @param userId userid that user enters, must exits if user wants to login
     */
    public void tryLogin(String userId){
        UserLogin command = new UserLoginImpl(
                this,
                userId);

        command.execute();
    }

    @Override
    public void onLoginPatientSuccess(Patient patient) {
        this.view.onLoginPatient(patient);
    }

    @Override
    public void onLoginCareProviderSuccess(CareProvider careProvider) {
        this.view.onLoginCareProvider(careProvider);
    }

    @Override
    public void onLoginInvalidUserId() {
        this.view.onInvalidUserId();
    }

    @Override
    public void onLoginUserDoesNotExist() {
        this.view.onUserDoesNotExist();
    }

    @Override
    public void onLoginCouldNotDetemineUserType() {

    }

}
