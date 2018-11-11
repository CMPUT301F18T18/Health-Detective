package cmput301f18t18.health_detective;

import android.os.Handler;
import android.os.Looper;

import cmput301f18t18.health_detective.domain.executor.MainThreadContract;

public class MainThreadImpl implements MainThreadContract {

    private static MainThreadContract mainThread = null;

    private Handler handler;

    public static MainThreadContract getInstance() {
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