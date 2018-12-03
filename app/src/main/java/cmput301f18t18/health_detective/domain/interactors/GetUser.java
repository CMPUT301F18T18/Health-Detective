package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

public interface GetUser extends Interactor {
    interface Callback {
        void onGetUserPatientSuccess(Patient patient);
        void onGetUserProviderSuccess(CareProvider careProvider);
        void onGetUserInvalidUserId();
        void onGetUserDoesNotExist();
    }
}
