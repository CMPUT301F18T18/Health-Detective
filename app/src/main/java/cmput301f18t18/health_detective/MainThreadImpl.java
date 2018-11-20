package cmput301f18t18.health_detective;

import android.os.Handler;
import android.os.Looper;

import cmput301f18t18.health_detective.domain.executor.MainThread;

public class MainThreadImpl implements MainThread {

    private static MainThread mainThread = null;

    private Handler handler;

    public static MainThread getInstance() {
        if (mainThread == null) {
            mainThread = new MainThreadImpl();
        }

        return mainThread;
    }

    private MainThreadImpl() {

        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        this.handler.post(runnable);
    }
}