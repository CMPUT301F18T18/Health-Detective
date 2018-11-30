package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddBodyLocation;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.model.images.base.DomainImage;
import cmput301f18t18.health_detective.domain.repository.BodyLocationRepo;

class AddBodyLocationImpl extends AbstractInteractor implements AddBodyLocation {

    private AddBodyLocation.Callback callback;
    private String label;
    private DomainImage frontImage;
    private DomainImage backImage;

    public AddBodyLocationImpl(AddBodyLocation.Callback callback,
                               String label, DomainImage frontImage, DomainImage backImage) {
        this.callback = callback;
        this.label = label;
        this.frontImage = backImage;
    }

    @Override
    public void run() {
        final BodyLocationRepo bodyLocationRepo = context.getBodyLocationRepo();
        final BodyLocation bodyLocation;
        final Patient patient;

        // Check to see if provided an image
        if (frontImage == null || backImage == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                   callback.onABLNoImage();
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

        // Get patient context
        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        patient = treeParser.getCurrentPatientContext();
        User loggedInUser = treeParser.getLoggedInUser();

        if (!patient.equals(loggedInUser) || patient == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onABLInvalidPermissions();
                }
            });

            return;
        }

        // We have ensured that we have the appropriate parameter's at this point
        // Generate new body image object
        bodyLocation = new BodyLocation(patient.getUserId(), label, frontImage, backImage);

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
