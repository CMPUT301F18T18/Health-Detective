package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.GetAssignedPatients;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class GetAssignedPatientsImpl extends AbstractInteractor implements GetAssignedPatients {

    private GetAssignedPatients.Callback callback;
    private UserRepo users;
    private CareProvider careProvider;

    public GetAssignedPatientsImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                   GetAssignedPatients.Callback callback, UserRepo users,
                                   CareProvider careProvider)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.users = users;
        this.careProvider = careProvider;
    }

    @Override
    public void run() {
        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){
        
            @Override
            public void run() {
                callback.onGAPFail();
            }
        });
    }
}