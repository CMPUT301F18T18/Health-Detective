package cmput301f18t18.health_detective.domain.executor.mock;

import cmput301f18t18.health_detective.domain.executor.MainThread;

public class MainThreadMock implements MainThread {
    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }
}
