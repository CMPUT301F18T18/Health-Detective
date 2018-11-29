package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddPhoto;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

class AddPhotoImpl extends AbstractInteractor implements AddPhoto {

    private Callback callback;
    private byte[] image;

    public AddPhotoImpl(Callback callback, byte[] image) {
        this.callback = callback;
        this.image = image;
    }

    @Override
    public void run() {

    }
}
