package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface RemoveAssignedPatientContract extends InteractorContract {
    public interface Callback {
        void onRAPSuccess(Patient removedPatient);
        void onRAPFail();
    }
}