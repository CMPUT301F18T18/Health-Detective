package cmput301f18t18.health_detective.domain.executor;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

public interface ThreadExecutorContract {
    void execute(final AbstractInteractor interactor);
}