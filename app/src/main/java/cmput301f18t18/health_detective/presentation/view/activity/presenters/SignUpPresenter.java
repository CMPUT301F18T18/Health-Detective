package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.widget.Toast;

//import cmput301f18t18.health_detective.domain.interactors.CreateUserProfileContract;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.interactors.impl.mock.CreateUserProfileMockSuccess;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;


public class SignUpPresenter implements CreateUserProfile.Callback {

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private UserRepo userRepo;

    public SignUpPresenter(ThreadExecutor threadExecutor, MainThread mainThread,
                           UserRepo userRepo)
    {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.userRepo = userRepo;
    }


    public void createNewUser(String userName, String userEmail, String userPhoneNum){
        // Need a way to inject type of user
        CreateUserProfile createUserProfile = new CreateUserProfileMockSuccess(
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
        // Not sure what you do with this information but here it is
    }

    @Override
    public void onCUPCareProviderSuccess(CareProvider careProvider) {
        // Not sure what you do with this information but here it is
    }

    @Override
    public void onCUPFail() {

    }
}
