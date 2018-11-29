package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.ViewUserDetails;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

class ViewUserDetailsImpl extends AbstractInteractor implements ViewUserDetails {

    private Callback callback;

    public ViewUserDetailsImpl(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
