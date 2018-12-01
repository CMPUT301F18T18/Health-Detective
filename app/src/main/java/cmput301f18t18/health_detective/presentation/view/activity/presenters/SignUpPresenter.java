package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cmput301f18t18.health_detective.domain.interactors.EditUserProfile;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateUserProfileImpl;
import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.interactors.impl.EditUserProfileImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetLoggedInUserImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.PutContext;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;


public class SignUpPresenter implements CreateUserProfile.Callback, EditUserProfile.Callback, GetLoggedInUser.Callback {

    private View view;

    @Override
    public void onGLIUNoUserLoggedIn() {
        view.onIsSignup();
    }

    @Override
    public void onGLIUPatient(Patient patient) {
        view.onIsEditPatient(patient);
    }

    @Override
    public void onGLIUCareProvider(CareProvider careProvider) {
        view.onIsEditCareProvider(careProvider);
    }

    public interface View {
        void onIsSignup();
        void onIsEditCareProvider(CareProvider careProvider);
        void onIsEditPatient(Patient patient);
        void onCreatePatient();
        void onCreateCareProvider();
        void onInvalidId();
        void onInvalidEmail();
        void onInvalidPhoneNumber();
        void onEditUserSuccess();
    }

    public SignUpPresenter(View view)
    {
        this.view = view;

        new GetLoggedInUserImpl(this).execute();
    }

    /**
     * Method that calls on interactor that will create a new user
     * @param userName new user name
     * @param userEmail new user email
     * @param userPhoneNum new user phone number
     */
    public void createNewUser(String userName, String userEmail, String userPhoneNum, Boolean isCareProvider){
        // Need a way to inject type of user
        CreateUserProfile createUserProfile = new CreateUserProfileImpl(
                this,
                userName,
                userEmail,
                userPhoneNum,
                isCareProvider
        );

        createUserProfile.execute();
    }

    /**
     * Method that calls on interactor that will edit the current user info
     * @param email new user email
     * @param phoneNumber new user phonenumber
     */
    public void editUserInfo(String email, String phoneNumber){
        EditUserProfile editUserProfile = new EditUserProfileImpl(
                this,
                email,
                phoneNumber
        );
        editUserProfile.execute();
    }


    @Override
    public void onCUPPatientSuccess(Patient patient) {
        new PutContext(patient).execute();

        this.view.onCreatePatient();
    }

    @Override
    public void onCUPCareProviderSuccess(CareProvider careProvider) {
        new PutContext(careProvider).execute();

        this.view.onCreateCareProvider();

    }

    @Override
    public void onCUPInvalidID() {
        this.view.onInvalidId();
    }

    @Override
    public void onCUPInvalidEmail() {
        this.view.onInvalidEmail();

    }

    @Override
    public void onCUPInvalidPhoneNumber() {
        this.view.onInvalidPhoneNumber();
    }

    @Override
    public void onCUPFail() {
    }

    @Override
    public void onEUPSuccess(User userProfile) {
        new PutContext(userProfile).execute();

        this.view.onEditUserSuccess();
    }

    @Override
    public void onEUPInvalidEmail() {

    }

    @Override
    public void onEUPInvaildPhoneNumber() {

    }

    @Override
    public void onEUPInvalidPermissions() {

    }

}
