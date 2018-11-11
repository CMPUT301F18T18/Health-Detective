package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface GetPatientsProblemsContract extends InteractorContract {
    public interface Callback {
        void onGPPSuccess(ArrayList<Problem> patientProblems);
        void onGPPFail();
    }
}