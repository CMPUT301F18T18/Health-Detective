package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class PatientContext extends AbstractContextTreeComponent {
    private final Patient patient;

    public PatientContext(Class<? extends AbstractInteractor> commandContext, Patient patient) {
        super(commandContext);
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }
}
