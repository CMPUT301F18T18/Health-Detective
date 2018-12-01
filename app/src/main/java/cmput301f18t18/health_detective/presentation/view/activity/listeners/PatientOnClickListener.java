package cmput301f18t18.health_detective.presentation.view.activity.listeners;

import cmput301f18t18.health_detective.domain.model.Patient;

public interface PatientOnClickListener {
    void onPatientClicked(Patient patient);
    void onDeleteClicked(Patient patient);
}
