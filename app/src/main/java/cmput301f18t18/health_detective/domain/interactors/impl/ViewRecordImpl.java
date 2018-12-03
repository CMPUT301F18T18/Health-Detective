package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.ViewRecord;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.ImageRepo;

public class ViewRecordImpl extends AbstractInteractor implements ViewRecord {

    private Callback callback;

    public ViewRecordImpl(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        if (callback == null)
            return;

        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        final Record record = treeParser.getCurrentRecordContext();
        final ImageRepo imageRepo = context.getImageRepo();
        final ArrayList<DomainImage> images;

        if (record == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVRInvalidRecord();
                }
            });
        }

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onVRSuccessDetails(record.getTitle(), record.getComment(), record.getDate(), record.getGeolocation());
            }
        });

        images = imageRepo.retrieveImagesByIds(record.getPhotos());

        if (images.isEmpty()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVRNoImages();
                }
            });
        }

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onVRSuccessImages(images);
            }
        });

    }
}
