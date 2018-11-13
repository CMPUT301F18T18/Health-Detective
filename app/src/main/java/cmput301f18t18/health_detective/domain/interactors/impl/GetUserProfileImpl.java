package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetUserProfile;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class GetUserProfileImpl extends AbstractInteractor implements GetUserProfile {

    private GetUserProfile.Callback callback;
    private UserRepo userRepo;
    private User user;

    public GetUserProfileImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                              GetUserProfile.Callback callback, UserRepo userRepo,
                              User user)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.user = user;
    }

    @Override
    public void run() {
        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGUPFail();
            }
        });
    }
}
