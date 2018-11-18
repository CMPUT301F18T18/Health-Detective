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
    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private UserRepo userRepo;

    public interface View {
        void onLoginPatient(Patient patient);
        void onLoginCareProvider(CareProvider careProvider);
        void onInvalidUserId();
        void onUserDoesNotExist();
    }

    public LoginPresenter(View view, ThreadExecutor threadExecutor, MainThread mainThread,
                           UserRepo userRepo)
    {
        this.view = view;
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.userRepo = userRepo;
    }

    public void tryLogin(String userId){

        UserLogin command = new UserLoginImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.userRepo,
                userId
        );

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
