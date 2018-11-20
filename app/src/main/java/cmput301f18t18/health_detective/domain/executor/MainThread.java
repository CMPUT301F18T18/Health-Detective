package cmput301f18t18.health_detective.domain.executor;

public interface MainThread {
    void post(final Runnable runnable);
}