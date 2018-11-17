package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface UserLogin extends Interactor {
    interface Callback {
        void onLoginPatientSuccess(Patient patient);
        void onLoginCareProviderSuccess(CareProvider careProvider);
        void onLoginInvalidUserId();
        void onLoginUserDoesNotExist();
        void onLoginCouldNotDetemineUserType();
    }
}