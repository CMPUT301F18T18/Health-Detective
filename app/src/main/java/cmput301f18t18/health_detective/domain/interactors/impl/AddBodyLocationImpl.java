package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddBodyLocation;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;

class AddBodyLocationImpl extends AbstractInteractor implements AddBodyLocation {

    private AddBodyLocation.Callback callback;
    private String label;
    private BodyLocation bodyLocation;
    private Integer xPos;
    private Integer yPos;

    public AddBodyLocationImpl(AddBodyLocation.Callback callback,
                               String label, BodyLocation bodyLocation, Integer xPos, Integer yPos) {
        this.callback = callback;
        this.label = label;
        this.bodyLocation = bodyLocation;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void run() {

    }
}
