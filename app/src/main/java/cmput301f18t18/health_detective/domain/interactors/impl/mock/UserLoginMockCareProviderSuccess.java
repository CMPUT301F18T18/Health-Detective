package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class UserLoginMockCareProviderSuccess extends AbstractInteractor implements UserLogin {

    private UserLogin.Callback callback;
    private UserRepo userRepo;
    private String userId;

    public UserLoginMockCareProviderSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                            UserLogin.Callback callback, UserRepo userRepo,
                                            String userId)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.userId = userId;
    }

    @Override
    public void run() {
        final CareProvider careProvider = new CareProvider(
                this.userId,
                "780-777-6666",
                this.userId + "@email.com"
        );

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLoginCareProviderSuccess(careProvider);
            }
        });
    }
}
