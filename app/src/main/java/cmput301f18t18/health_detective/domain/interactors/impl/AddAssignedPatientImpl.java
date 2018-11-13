package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class AddAssignedPatientImpl extends AbstractInteractor implements AddAssignedPatient {

    private AddAssignedPatient.Callback callback;
    private UserRepo users;
    private CareProvider careProvider;
    private String patientId;

    public AddAssignedPatientImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                  AddAssignedPatient.Callback callback, UserRepo users,
                                  CareProvider careProvider, String patientId)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.users = users;
        this.careProvider = careProvider;
        this.patientId = patientId;
    }

    @Override
    public void run() {
        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){
        
            @Override
            public void run() {
                callback.onAAPFail();
            }
        });
    }
}