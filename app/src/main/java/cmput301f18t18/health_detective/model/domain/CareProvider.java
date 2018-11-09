package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;

public class CareProvider extends User {
    public ArrayList<Patient> patients;

    public CareProvider() {
        patients = new ArrayList<>();
    }

    public void AddPatient(Patient pat) {
        patients.add(pat);
    }

    public Patient GetPatient(int index) {
        return patients.get(index);
    }
}
