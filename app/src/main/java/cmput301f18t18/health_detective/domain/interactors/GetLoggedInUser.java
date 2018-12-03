package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface GetLoggedInUser extends Interactor {
    interface Callback {
        void onGLIUNoUserLoggedIn();
        void onGLIUPatient(Patient patient);
        void onGLIUCareProvider(CareProvider careProvider);
    }
}
