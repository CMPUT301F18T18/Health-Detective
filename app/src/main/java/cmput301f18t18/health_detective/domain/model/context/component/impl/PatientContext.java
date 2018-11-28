package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class PatientContext extends AbstractContextTreeComponent {
    private final Patient patient;

    public PatientContext(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }
}
