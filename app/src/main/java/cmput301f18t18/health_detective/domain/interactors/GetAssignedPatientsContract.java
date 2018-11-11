package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface GetAssignedPatientsContract extends InteractorContract {
    public interface Callback {
        void onGAPSuccess(ArrayList<Patient> assignedPatients);
        void onGAPFail();
    }
}