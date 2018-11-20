package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface CreateProblem extends Interactor {
    interface Callback {
        void onCPSuccess(Problem problem);
        void onCPNullTitle();
        void onCPFail();
    }
}