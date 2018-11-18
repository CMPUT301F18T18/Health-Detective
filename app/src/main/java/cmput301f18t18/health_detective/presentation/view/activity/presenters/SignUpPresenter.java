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

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private UserRepo userRepo;
    private Context context;

    public SignUpPresenter(ThreadExecutor threadExecutor, MainThread mainThread,
                           UserRepo userRepo)
    {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.userRepo = userRepo;
    }


    public void createNewUser(Context context, String userName, String userEmail, String userPhoneNum){
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
        // Not sure what you do with this information but here it is
        Intent intent = new Intent(context, PatientProblemsActivity.class);
        intent.putExtra("PATIENT", patient);
        //Toast.makeText(context, "Accounted created, logging in", Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }

    @Override
    public void onCUPCareProviderSuccess(CareProvider careProvider) {
        // Not sure what you do with this information but here it is
    }

    @Override
    public void onCUPInvalidID() {
        //Toast.makeText(context, "Invalid Id", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCUPInvalidEmail() {
        //Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCUPInvalidPhoneNumber() {
        //Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCUPFail() {
        //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
    }

}
