package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.ViewBodyLocations;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

class ViewBodyLocationsImpl extends AbstractInteractor implements ViewBodyLocations {

    private Callback callback;

    public ViewBodyLocationsImpl(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
