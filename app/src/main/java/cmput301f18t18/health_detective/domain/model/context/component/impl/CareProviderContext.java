package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

/**
 * Careprovider context tree component, stores a careprovider in the context tree
 */
public class CareProviderContext extends AbstractContextTreeComponent {
    private final CareProvider careProvider;

    public CareProviderContext(CareProvider careProvider) {
        super();
        this.careProvider = careProvider;
    }

    public CareProvider getCareProvider() {
        return careProvider;
    }
}
