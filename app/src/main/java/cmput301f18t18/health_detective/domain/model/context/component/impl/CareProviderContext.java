package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class CareProviderContext extends AbstractContextTreeComponent {
    private final CareProvider careProvider;

    public CareProviderContext(CareProvider careProvider) {
        this.careProvider = careProvider;
    }


    public CareProvider getCareProvider() {
        return careProvider;
    }
}
