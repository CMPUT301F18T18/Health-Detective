package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.DeleteRecordPhoto;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.DomainImage;

class DeletePhotoImpl extends AbstractInteractor implements DeleteRecordPhoto {

    private Callback callback;
    private DomainImage image;

    public DeletePhotoImpl(Callback callback, DomainImage image) {
        this.callback = callback;
        this.image = image;
    }


    @Override
    public void run() {

    }
}
