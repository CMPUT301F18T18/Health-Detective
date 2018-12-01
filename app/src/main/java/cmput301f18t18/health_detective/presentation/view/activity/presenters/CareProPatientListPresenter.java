package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.view.View;

import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.RemoveAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.impl.AddAssignedPatientImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.RemoveAssignedPatientImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

public class CareProPatientListPresenter implements AddAssignedPatient.Callback, RemoveAssignedPatient.Callback{

    private View view;


    public interface View {
        void onAddPatientSuccess();
        void onDeletePatientSuccess();
    }

    public CareProPatientListPresenter(View view) {this.view = view;}

    public void addNewPatient(String patientId){
        AddAssignedPatient command = new AddAssignedPatientImpl(
                this,
                null,
                patientId
        );

        command.execute();
    }

    public void deletePatient(CareProvider careProvider, Patient patient){
        RemoveAssignedPatient command = new RemoveAssignedPatientImpl(
                this,
                careProvider,
                patient
        );

        command.execute();
    }

    @Override
    public void onAAPSuccess() {
        this.view.onAddPatientSuccess();
    }

    @Override
    public void onAAPNotValidUserId() {

    }

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
        this.view.onDeletePatientSuccess();

    }

    @Override
    public void onRAPPatientNotAssigned() {

    }

    @Override
    public void onRAPInvalidPermissions() {

    }
}
