package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cmput301f18t18.health_detective.domain.interactors.impl.CreateUserProfileImpl;
import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;


public class SignUpPresenter implements CreateUserProfile.Callback {

    private View view;
    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private UserRepo userRepo;

    public interface View {
        void onCreatePatient(Patient patient);
        void onCreateCareProvider(CareProvider careProvider);
        void onInvalidId();
        void onInvalidEmail();
        void onInvalidPhoneNumber();
    }

    public SignUpPresenter(View view, ThreadExecutor threadExecutor, MainThread mainThread,
                           UserRepo userRepo)
    {
        this.view = view;
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.userRepo = userRepo;
    }


    public void createNewUser(String userName, String userEmail, String userPhoneNum){
        // Need a way to inject type of user
        CreateUserProfile createUserProfile = new CreateUserProfileImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.userRepo,
                userName,
                userEmail,
                userPhoneNum,
                false
        );

        createUserProfile.execute();
    }


    @Override
    public void onCUPPatientSuccess(Patient patient) {
        this.view.onCreatePatient(patient);
    }

    @Override
    public void onCUPCareProviderSuccess(CareProvider careProvider) {
        // Not sure what you do with this information but here it is

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

}
