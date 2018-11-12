package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface DeleteProblem extends Interactor {
    public interface Callback {
        void onDPSuccess(Problem problem);
        void onDPFail();
    }
}