package cmput301f18t18.health_detective.domain.executor;

public interface MainThreadContract {
    void post(final Runnable runnable);
}