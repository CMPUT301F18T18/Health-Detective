package cmput301f18t18.health_detective.domain.model;

import java.util.HashMap;

public class CareProvider extends User {
    public HashMap<String, Patient> patients;

    public CareProvider() {
        patients = new HashMap<>();
    }

    public void AddPatient(Patient pat) {
        patients.put(pat.getUserID(), pat);
    }

    public Patient GetPatient(String patientId) {
        return patients.get(patientId);
    }
}
