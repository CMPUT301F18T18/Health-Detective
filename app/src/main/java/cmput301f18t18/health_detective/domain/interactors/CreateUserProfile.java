package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;

public interface CreateUserProfile extends Interactor {
    interface Callback {
        void onCUPPatientSuccess(Patient patient);
        void onCUPCareProviderSuccess(CareProvider careProvider);
        void onCUPInvalidID();
        void onCUPInvalidEmail();
        void onCUPInvalidPhoneNumber();
    }
}