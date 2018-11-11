package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.User;

public interface UserLoginContract extends InteractorContract {
    public interface Callback {
        void onLoginSuccess(User user);
        void onLoginFail();
    }
}