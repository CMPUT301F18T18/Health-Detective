package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.AddPhoto;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.ImageRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class AddPhotoImpl extends AbstractInteractor implements AddPhoto {

    private Callback callback;
    private String label;
    private String image;
    private boolean type;
    private boolean leftRight;

    public AddPhotoImpl(Callback callback, String label, String image, boolean type, boolean leftRight) {
        this.callback = callback;
        this.label = label;
        this.image = image;
        this.type = type;
        this.leftRight = leftRight;
    }

    @Override
    public void run() {
        final ImageRepo imageRepo = context.getImageRepo();
        final RecordRepo recordRepo = context.getRecordRepo();
        final Patient patient;
        final User loggedInUser;
        final Record recordContext;
        final DomainImage newImage;

        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        loggedInUser = treeParser.getLoggedInUser();
        patient = treeParser.getCurrentPatientContext();
        recordContext = treeParser.getCurrentRecordContext();

        if (patient == null || !patient.equals(loggedInUser)) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    if (callback == null)
                        return;

                    callback.onAPhInvalidPermissions();
                }
            });

            return;
        }

        // No image!
        if (image == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    if (callback == null)
                        return;

                    callback.onAPhNoImage();
                }
            });

            return;
        }

        newImage = new DomainImage(image);

        if (type) {
            if (label != null && label != "")
                newImage.setLabel(label);

            patient.addBodylocation(newImage);
            context.getUserRepo().updateUser(patient);

            if (recordContext != null) {
                if (leftRight) {
                    recordContext.setBodyloaction2(newImage);
                }
                else {
                    recordContext.setBodyloaction1(newImage);
                }

                recordRepo.updateRecord(recordContext);
            }
        } else {
            recordContext.insertPhoto(newImage);
            recordRepo.updateRecord(recordContext);
        }

        imageRepo.insertImage(newImage);

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onAPhSuccess(newImage);
            }
        });

    }
}
