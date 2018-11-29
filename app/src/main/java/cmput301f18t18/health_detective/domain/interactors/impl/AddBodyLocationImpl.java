package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddBodyLocation;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.images.BodyImage;
import cmput301f18t18.health_detective.domain.repository.BodyLocationRepo;

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
        final BodyLocationRepo bodyLocationRepo = context.getBodyLocationRepo();
        final BodyLocation bodyLocation;

        // Check to see if provided an image
        if (bodyImage == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                   callback.onABLNoImage();
                }
            });

            return;
        }

        // Check to see if provided with a valid coordinate
        if (xPos == null || yPos == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onABLInvalidCoordinate();
                }
            });

            return;
        }

        // Check to see if label was provided, no constraint on RegEx
        if (label == null || label == "") {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onABLEmptyLabel();
                }
            });

            return;
        }

        // We have ensured that we have the appropriate parameter's at this point
        // Generate new body image object
        bodyLocation = new BodyLocation(label, bodyImage, xPos, yPos);

        // Return object to main thread so it does not have to wait on database
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onABLSuccess(bodyLocation);
            }
        });

        // Store new body location
        bodyLocationRepo.insertBodyLocation(bodyLocation);
    }
}
