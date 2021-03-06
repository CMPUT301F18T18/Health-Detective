package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface DeleteProblem extends Interactor {
    interface Callback {
        void onDPSuccess(Problem problem);
        void onDPPatientNotFound();
        void onDPProblemNotFound();
        void onDPFail();
    }
}