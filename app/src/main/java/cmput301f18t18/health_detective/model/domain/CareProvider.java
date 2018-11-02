package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;

public class CareProvider extends User {
    public ArrayList<Patient> Patients;

    public void AddPatient(Patient pat) {
        Patients.add(pat);
    }

    public Patient GetPatient(int index) {
        return Patients.get(index);
    }
}
