package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.text.GetChars;
import android.view.View;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.RemoveAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.ViewCareProvider;
import cmput301f18t18.health_detective.domain.interactors.impl.AddAssignedPatientImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetLoggedInUserImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.Logout;
import cmput301f18t18.health_detective.domain.interactors.impl.PutContext;
import cmput301f18t18.health_detective.domain.interactors.impl.RemoveAssignedPatientImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.ViewCareProviderImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

public class CareProPatientListPresenter implements AddAssignedPatient.Callback, RemoveAssignedPatient.Callback, ViewCareProvider.Callback, GetLoggedInUser.Callback{

    private View view;

    @Override
    public void onVCPSuccess(ArrayList<Patient> assignedPatients) {
        this.view.onGetPatientSuccess(assignedPatients);
    }

    @Override
    public void onVCPSuccessDetails(String userId, String email, String phone) {

    }

    @Override
    public void onVCPNoPatients() {
        this.view.noPatients();
    }

    @Override
    public void onVCPNoContext() {

    }


    public interface View {
        void onAddPatientSuccess();
        void onAddPatientFailure();
        void onDeletePatientSuccess(Patient patient);
        void onGetPatientSuccess(ArrayList<Patient> assignedPatients);
        void noPatients();
        void onGetUser(CareProvider careProvider);
        void onClickPatient();
        void onLogout();
    }

    public CareProPatientListPresenter(View view) {
        this.view = view;
        new GetLoggedInUserImpl(this).execute();
    }

    public void addNewPatient(String patientId){
        AddAssignedPatient command = new AddAssignedPatientImpl(
                this,
                null,
                patientId
        );

        command.execute();
    }

    public void deletePatient(Patient patient){
        RemoveAssignedPatient command = new RemoveAssignedPatientImpl(
                this,
                null,
                patient
        );

        command.execute();
    }

    public void getAssignedPatients() {
        ViewCareProvider command = new ViewCareProviderImpl(
                this
        );
        command.execute();
    }

    public void clickOnPatient(Patient patient){
        new PutContext(patient).execute();
        this.view.onClickPatient();
    }

    public void onLogout() {
        new Logout().execute();

        view.onLogout();
    }

    @Override
    public void onAAPSuccess() {
        //new PutContext(patient).execute();
        this.view.onAddPatientSuccess();
    }

    @Override
    public void onAAPNotValidUserId() {this.view.onAddPatientFailure();}


    @Override
    public void onAAPPatientAlreadyAssigned() {

    }

    @Override
    public void onAAPPatientDoesNotExist() {

    }

    @Override
    public void onAAPLoggedInUserNotACareProvider() {

    }

    @Override
    public void onRAPSuccess(Patient removedPatient) {
        this.view.onDeletePatientSuccess(removedPatient);

    }

    @Override
    public void onRAPPatientNotAssigned() {

    }

    @Override
    public void onRAPInvalidPermissions() {

    }

    @Override
    public void onGLIUNoUserLoggedIn() {

    }

    @Override
    public void onGLIUPatient(Patient patient) {

    }

    @Override
    public void onGLIUCareProvider(CareProvider careProvider) {
        this.view.onGetUser(careProvider);
    }
}
