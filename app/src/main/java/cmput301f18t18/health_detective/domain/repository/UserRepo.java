package cmput301f18t18.health_detective.domain.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;

public interface UserRepo {
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);

    Patient retrievePatientById(String patientId);
    ArrayList<Patient> retrievePatientsById(ArrayList<String> patientIds);
    CareProvider retrieveCareProviderById(String careProviderId);

    boolean validateUserIdUniqueness(String userId);
}
