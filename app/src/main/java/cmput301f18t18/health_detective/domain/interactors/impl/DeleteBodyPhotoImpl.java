package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.DeleteBodyPhoto;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.images.BodyImage;

class DeleteBodyPhotoImpl extends AbstractInteractor implements DeleteBodyPhoto {

    private Callback callback;
    private BodyImage bodyImage;

    public DeleteBodyPhotoImpl(Callback callback, BodyImage bodyImage) {
        this.callback = callback;
        this.bodyImage = bodyImage;
    }


    @Override
    public void run() {

    }
}
