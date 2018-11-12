package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.User;

public interface GetUserProfile extends Interactor {
    public interface Callback {
        void onGUPSuccess(User userProfile);
        void onGUPFail();
    }
}