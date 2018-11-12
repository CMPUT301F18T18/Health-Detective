package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface GetPatientsProblems extends Interactor {
    public interface Callback {
        void onGPPSuccess(ArrayList<Problem> patientProblems);
        void onGPPFail();
    }
}