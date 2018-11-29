package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddBodyPhoto;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;

class AddBodyPhotoImpl extends AbstractInteractor implements AddBodyPhoto {

    private Callback callback;
    private DomainImage frontImage;
    private DomainImage backImage;

    public AddBodyPhotoImpl(Callback callback,
                            DomainImage frontImage, DomainImage backImage) {
        this.callback = callback;
        this.frontImage = frontImage;
        this.backImage = backImage;
    }


    @Override
    public void run() {

    }
}
