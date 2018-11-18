package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;
import android.content.Intent;

import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;

public class LoginPresenter implements UserLogin.Callback {

    String userId;
    Context context;

    public void tryLogin(Context context, String userId){
        this.context = context;


    }

    @Override
    public void onLoginPatientSuccess(Patient patient) {
        Intent intent = new Intent(context, PatientProblemsActivity.class);
        intent.putExtra("PATIENT", patient);
        //Toast.makeText(context, "Accounted created, logging in", Toast.LENGTH_SHORT).show();
        context.startActivity(intent);

    }

    @Override
    public void onLoginCareProviderSuccess(CareProvider careProvider) {

    }

    @Override
    public void onLoginInvalidUserId() {

    }

    @Override
    public void onLoginUserDoesNotExist() {

    }

    @Override
    public void onLoginCouldNotDetemineUserType() {

    }

}
