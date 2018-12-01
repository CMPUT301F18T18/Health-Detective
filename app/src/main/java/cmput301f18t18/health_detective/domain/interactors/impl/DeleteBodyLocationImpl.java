package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.DeleteBodyLocation;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;

class DeleteBodyLocationImpl extends AbstractInteractor implements DeleteBodyLocation {

    private Callback callback;
    private BodyLocation bodyLocation;

    public DeleteBodyLocationImpl(Callback callback, BodyLocation bodyLocation) {
        this.callback = callback;
        this.bodyLocation = bodyLocation;
    }


    @Override
    public void run() {

    }
}
