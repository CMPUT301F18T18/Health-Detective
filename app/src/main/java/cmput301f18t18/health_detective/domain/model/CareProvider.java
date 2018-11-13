package cmput301f18t18.health_detective.domain.model;

import java.util.HashMap;
import java.util.HashSet;

public class CareProvider extends User {
    public HashSet<String> patients;

    public CareProvider() {
        super();
        patients = new HashSet<>();
    }

    public CareProvider(String userId) {
        super(userId);
        patients = new HashSet<>();
    }

    public CareProvider(String userId, String phoneNumber, String emailAddress) {
        super(userId, phoneNumber, emailAddress);
        patients = new HashSet<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient.getUserID());
    }

    public void addPatient(String patientId) {
        patients.add(patientId);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient.getUserID());
    }

    public void removePatient(String patientId) {
        patients.remove(patientId);
    }

    public boolean hasPatient(String patientId) {
        if (this.patients.contains(patientId)) {
            return true;
        }

        return false;
    }
}
