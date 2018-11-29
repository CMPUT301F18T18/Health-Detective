package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class BodyLocationContext extends AbstractContextTreeComponent {

    private final BodyLocation bodyLocation;

    public BodyLocationContext(Class<? extends AbstractInteractor> commandContext, BodyLocation bodyLocation) {
        super(commandContext);
        this.bodyLocation = bodyLocation;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }
}
