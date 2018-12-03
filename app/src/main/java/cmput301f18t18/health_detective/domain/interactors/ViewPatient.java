package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface ViewPatient extends Interactor {
    interface Callback {
        void onVPaSuccess(ArrayList<Problem> patientProblems);
        void onVPaSuccessDetails(String userId, String email, String phone);
        void onVPaNoProblems();
        void onVPaNoContext();
    }
}