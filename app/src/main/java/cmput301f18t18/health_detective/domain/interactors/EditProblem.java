package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Problem;

public interface EditProblem extends Interactor {
    interface Callback {
        void onEPSuccess(Problem problem);
        void onEPEmptyTitle();
        void onEPNoStartDateProvided();
        void onEPInvalidPermissions();
    }
}