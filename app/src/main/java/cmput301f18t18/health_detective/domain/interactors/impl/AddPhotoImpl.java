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

        if (patient == null || !patient.equals(loggedInUser) || recordContext == null) {
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

        if (label != null && label != "")
            newImage.setLabel(label);

        if (xPos != null && yPos != null)
            newImage.setPos(xPos, yPos);

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onAPhSuccess(newImage);
            }
        });

        recordContext.insertPhoto(newImage);
        recordRepo.updateRecord(recordContext);
        imageRepo.insertImage(newImage);
    }
}
