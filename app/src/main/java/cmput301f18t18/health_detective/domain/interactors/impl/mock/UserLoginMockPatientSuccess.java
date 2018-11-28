package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class UserLoginMockPatientSuccess extends AbstractInteractor implements UserLogin {

    private UserLogin.Callback callback;
    private UserRepo userRepo;
    private String userId;

    public UserLoginMockPatientSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                       UserLogin.Callback callback, UserRepo userRepo,
                                       String userId)
    {
        super();
        this.callback = callback;
        this.userRepo = userRepo;
        this.userId = userId;
    }

    @Override
    public void run() {
        final Patient patient = new Patient(
                this.userId,
                "780-999-8888",
                this.userId + "@email.com"
        );

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLoginPatientSuccess(patient);
            }
        });
    }
}
