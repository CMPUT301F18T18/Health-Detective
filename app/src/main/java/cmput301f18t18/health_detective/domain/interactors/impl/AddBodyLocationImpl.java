package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddBodyLocation;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.images.BodyImage;

class AddBodyLocationImpl extends AbstractInteractor implements AddBodyLocation {

    private AddBodyLocation.Callback callback;
    private String label;
    private BodyImage bodyImage;
    private Integer xPos;
    private Integer yPos;

    public AddBodyLocationImpl(AddBodyLocation.Callback callback,
                               String label, BodyImage bodyImage, Integer xPos, Integer yPos) {
        this.callback = callback;
        this.label = label;
        this.bodyImage = bodyImage;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void run() {

    }
}
