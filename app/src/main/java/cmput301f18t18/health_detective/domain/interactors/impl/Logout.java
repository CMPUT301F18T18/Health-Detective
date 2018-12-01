package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

public class Logout extends AbstractInteractor {
    @Override
    public void run() {
        context.getContextTree().nuke();
    }

    @Override
    public void execute() {
        run();
    }
}
