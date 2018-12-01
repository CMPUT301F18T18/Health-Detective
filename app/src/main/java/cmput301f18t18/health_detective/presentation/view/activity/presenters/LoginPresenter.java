package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.interactors.impl.PutContext;
import cmput301f18t18.health_detective.domain.interactors.impl.UserLoginImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

public class LoginPresenter implements UserLogin.Callback {

    private View view;

    public interface View {
        void onLoginPatient();
        void onLoginCareProvider();
        void makeToast(String msg);
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
        new PutContext(patient).execute();

        this.view.makeToast("Logging In");
        this.view.onLoginPatient();
    }

    @Override
    public void onLoginCareProviderSuccess(CareProvider careProvider) {
        new PutContext(careProvider).execute();

        this.view.makeToast("Logging In");
        this.view.onLoginCareProvider();
    }

    @Override
    public void onLoginInvalidUserId() {
        this.view.makeToast("Invalid UserId");
    }

    @Override
    public void onLoginUserDoesNotExist() {
        this.view.makeToast("User does not exist");
    }

    @Override
    public void onLoginCouldNotDetemineUserType() {

    }

}
