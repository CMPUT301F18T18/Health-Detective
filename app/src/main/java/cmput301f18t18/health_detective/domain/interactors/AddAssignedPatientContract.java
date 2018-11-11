package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface AddAssignedPatientContract extends InteractorContract {
    public interface Callback {
        void onAAPSuccess(Patient patient);
        void onAAPFail();
    }
}