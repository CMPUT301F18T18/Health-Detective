package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface GetProblems extends Interactor {
    interface Callback {
        void onGPSuccess(ArrayList<Problem> patientProblems);
        void onGPNoProblems();
    }
}