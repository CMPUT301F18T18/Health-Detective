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

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private UserRepo userRepo;
    private Context context;

    public LoginPresenter(ThreadExecutor threadExecutor, MainThread mainThread,
                           UserRepo userRepo)
    {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.userRepo = userRepo;
    }

    public void tryLogin(Context context, String userId){
        this.context = context;

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
        // Not sure what you do with this information but here it is
        Intent intent = new Intent(context, PatientProblemsActivity.class);
        intent.putExtra("PATIENT", patient);
        Toast.makeText(context, "Logging in", Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }

    @Override
    public void onLoginCareProviderSuccess(CareProvider careProvider) {

    }

    @Override
    public void onLoginInvalidUserId() {
        Toast.makeText(context, "Invalid UserId", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginUserDoesNotExist() {
        Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginCouldNotDetemineUserType() {

    }

}
