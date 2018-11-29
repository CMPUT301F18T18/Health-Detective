package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.ViewBodyPhotos;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

class ViewBodyPhotosImpl extends AbstractInteractor implements ViewBodyPhotos {

    private Callback callback;

    public ViewBodyPhotosImpl(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
