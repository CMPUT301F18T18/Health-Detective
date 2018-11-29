package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.images.BodyImage;

public class BodyImageContext extends AbstractContextTreeComponent {

    private final BodyImage bodyImage;

    public BodyImageContext(Class<? extends AbstractInteractor> commandContext, BodyImage bodyImage) {
        super(commandContext);
        this.bodyImage = bodyImage;
    }

    public BodyImage getBodyImage() {
        return bodyImage;
    }
}
