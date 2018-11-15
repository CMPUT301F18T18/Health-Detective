package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class UserLoginImpl extends AbstractInteractor implements UserLogin {

    private UserLogin.Callback callback;
    private UserRepo userRepo;
    private String userId;

    public UserLoginImpl(ThreadExecutor threadExecutor, MainThread mainThread,
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
        final Patient patient;
        final CareProvider careProvider;

        if (!User.isValidUserId(userId)){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onLoginInvalidUserId();
                }
            });

            return;
        }

        patient = this.userRepo.retrievePatientById(userId);

        if (patient != null) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onLoginPatientSuccess(patient);
                }
            });

            return;
        }

        careProvider = this.userRepo.retrieveCareProviderById(userId);

        if (careProvider != null) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onLoginCareProviderSuccess(careProvider);
                }
            });

            return;
        }

        // Could not find user
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onLoginUserDoesNotExist();
            }
        });
    }
}
