package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.ViewRecord;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

class ViewRecordImpl extends AbstractInteractor implements ViewRecord {

    private Callback callback;

    public ViewRecordImpl(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
