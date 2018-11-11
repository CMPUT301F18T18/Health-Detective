package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface DeleteProblemContract extends InteractorContract {
    public interface Callback {
        void onDPSuccess(Problem problem);
        void onDPFail();
    }
}