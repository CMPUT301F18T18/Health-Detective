package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.ViewRecord;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;

class ViewRecordImpl extends AbstractInteractor implements ViewRecord {

    private Callback callback;
    private Record record;

    public ViewRecordImpl(Callback callback, Record record) {
        this.callback = callback;
        this.record = record;
    }

    @Override
    public void run() {
        if (record == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    if (callback == null)
                        return;

                    callback.onVRInvalidRecord();
                }
            });
        }

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                if (callback == null)
                    return;

                callback.onVRSuccess(record);
            }
        });

        // The main reason for this command, keep context maintained.
        ContextTree tree = context.getContextTree();
        tree.push(ContextTreeComponentFactory.getContextComponent(record));
    }
}
