package cmput301f18t18.health_detective.domain.executor.mock;

import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

public class ThreadExecutorMock implements ThreadExecutor {
    @Override
    public void execute(AbstractInteractor interactor) {
        interactor.run();
    }
}
