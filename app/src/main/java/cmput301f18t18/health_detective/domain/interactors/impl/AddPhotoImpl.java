package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddPhoto;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

class AddPhotoImpl extends AbstractInteractor implements AddPhoto {

    private Callback callback;
    private String label;
    private byte[] image;
    private Integer xPos;
    private Integer yPos;

    public AddPhotoImpl(Callback callback, String label, byte[] image, Integer xPos, Integer yPos) {
        this.callback = callback;
        this.label = label;
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void run() {

    }
}
