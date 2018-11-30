package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.ViewBodyLocations;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.BodyLocationRepo;

class ViewBodyLocationsImpl extends AbstractInteractor implements ViewBodyLocations {

    private Callback callback;

    public ViewBodyLocationsImpl(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        if (callback == null)
            return;

        final BodyLocationRepo bodyLocationRepo = context.getBodyLocationRepo();
        final Patient patientContext;
        final ArrayList<BodyLocation> bodyLocations;
        ContextTree tree = this.context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        patientContext = treeParser.getCurrentPatientContext();

        if (patientContext == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVBLInvalidPermissions();
                }
            });

            return;
        }

        bodyLocations = bodyLocationRepo.retrieveAllBodyLocations(patientContext.getUserId());

        if (bodyLocations.isEmpty()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVBLNoBodyLocations();
                }
            });

            return;
        }
        
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onVBLSuccess(bodyLocations);
            }
        });
    }
}
