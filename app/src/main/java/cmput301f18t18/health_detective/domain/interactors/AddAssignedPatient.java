package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface AddAssignedPatient extends Interactor {
    interface Callback {
        void onAAPSuccess();
        void onAAPFail();
    }
}