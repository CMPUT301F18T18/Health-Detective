package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.User;

public interface CreateUserProfile extends Interactor {
    interface Callback {
        void onCUPSuccess();
        void onCUPFail();
    }
}