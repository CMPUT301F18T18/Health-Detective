package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface CreateProblemContract extends InteractorContract {
    public interface Callback {
        void onCPSuccess(Problem problem);
        void onCPFail();
    }
}