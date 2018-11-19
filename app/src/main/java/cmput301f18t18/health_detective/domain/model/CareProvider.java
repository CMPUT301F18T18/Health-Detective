package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CareProvider extends User {
    private static final long serialVersionUID = 5L;
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
        patients.add(patient.getUserId());
    }

    public void addPatient(String patientId) {
        patients.add(patientId);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient.getUserId());
    }

    public void removePatient(String patientId) {
        patients.remove(patientId);
    }

    public boolean isPatientsEmpty() {
        return patients.isEmpty();
    }

    public ArrayList<String> getPatientIds() {
        ArrayList<String> patientIds = new ArrayList<>();

        if (this.isPatientsEmpty()) {
            return patientIds;
        }

        for (String patientId: this.patients) {
            patientIds.add(patientId);
        }

        return  patientIds;
    }

    public boolean hasPatient(String patientId) {
        if (patientId == null) {
            return false;
        }

        if (!this.patients.contains(patientId)) {
            return false;
        }

        return true;
    }

    public boolean hasPatient(Patient patient) {
        if (patient == null) {
            return false;
        }

        if (!this.patients.contains(patient.getUserId())) {
            return false;
        }

        return true;
    }
}
