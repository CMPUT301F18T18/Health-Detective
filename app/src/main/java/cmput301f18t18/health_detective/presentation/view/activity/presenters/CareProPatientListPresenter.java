package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.view.View;

import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.impl.AddAssignedPatientImpl;

public class CareProPatientListPresenter implements AddAssignedPatient.Callback{

    private View view;

    public interface View {
        void onAddPatientSuccess();
    }

    public CareProPatientListPresenter(View view) {this.view = view;}

    public void addNewPatient(String patientId){
        AddAssignedPatient command = new AddAssignedPatientImpl(
                this,
                null,
                patientId
        );
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
}
