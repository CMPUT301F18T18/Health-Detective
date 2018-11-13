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

    public void AddPatient(Patient patient) {
        patients.add(patient.getUserID());
    }

    public void AddPatient(String patientId) {
        patients.add(patientId);
    }

    public void RemovePatient(Patient patient) {
        patients.remove(patient.getUserID());
    }

    public void RemovePatient(String patientId) {
        patients.remove(patientId);
    }

    public boolean HasPatient(String patientId) {
        if (this.patients.contains(patientId)) {
            return true;
        }

        return false;
    }
}
