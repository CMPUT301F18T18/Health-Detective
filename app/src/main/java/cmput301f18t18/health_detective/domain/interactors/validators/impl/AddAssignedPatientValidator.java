package cmput301f18t18.health_detective.domain.interactors.validators.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.validators.base.AbstractInteractorValidator;
import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;

public class AddAssignedPatientValidator extends AbstractInteractorValidator {

    private String patientId;

    public AddAssignedPatientValidator(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public <T extends AbstractInteractor> boolean validateInteractorPermission(T interactor) {
        DomainContext context = DomainContext.getInstance();
        ContextTree contextTree = context.getContextTree();
        ContextTreeComponent treeTail = contextTree.getTail();

        // Is logged in user a careprovider
        if (!(treeTail instanceof CareProviderContext))
            return false;

        return true;
    }
}
