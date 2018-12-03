package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface ViewCareProvider extends Interactor {
    interface Callback {
        void onVCPSuccess(ArrayList<Patient> assignedPatients);
        void onVCPSuccessDetails(String userId, String email, String phone);
        void onVCPNoPatients();
        void onVCPNoContext();
    }
}