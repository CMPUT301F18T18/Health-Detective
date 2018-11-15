package cmput301f18t18.health_detective.domain.repository.mock;

import java.util.ArrayList;
import java.util.HashMap;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class UserRepoMock implements UserRepo {

    private HashMap < String, Patient> patients = new HashMap<>();
    private HashMap < String, CareProvider> careProviders = new HashMap<>();


    @Override
    public void insertUser(User user) {

        if (user == null) {
            return;
        }

        if (user instanceof Patient) {
            this.patients.put(user.getUserId(), (Patient) user);
        } else if (user instanceof CareProvider) {
            this.careProviders.put(user.getUserId(), (CareProvider) user);
        }
    }

    @Override
    public void updateUser(User user) {
        this.insertUser(user);
    }

    @Override
    public void deleteUser(User user) {
        if (user == null) {
            return;
        }

        if (user instanceof Patient) {
            this.patients.remove(user.getUserId());
        } else if (user instanceof CareProvider) {
            this.careProviders.remove(user.getUserId());
        }
    }

    @Override
    public Patient retrievePatientById(String patientId) {
        if (patientId == null) {
            return null;
        }

        return this.patients.get(patientId);
    }

    @Override
    public ArrayList < Patient > retrievePatientsById (ArrayList<String> patientIds) {
        ArrayList<Patient> patientList = new ArrayList<>();

        if (patientIds == null || patientIds.isEmpty()) {
            return patientList;
        }

        for (Patient patient: this.patients.values()) {
            patientList.add(patient);
        }

        return patientList;
    }


    @Override
    public CareProvider retrieveCareProviderById(String careProviderId) {
        if (careProviderId == null) {
            return null;
        }

        return this.careProviders.get(careProviderId);
    }

    @Override
    public boolean validateUserIdUniqueness(String userId) {
        if (patients.containsKey(userId)) {
            return false;
        } else if (careProviders.containsKey(userId)) {
            return false;
        }

        return true;
    }
}