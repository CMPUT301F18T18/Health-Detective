package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.RemoveAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class RemoveAssignedPatientImpl extends AbstractInteractor implements RemoveAssignedPatient {

    private RemoveAssignedPatient.Callback callback;
    private UserRepo users;
    private CareProvider careProvider;
    private Patient patient;

    public RemoveAssignedPatientImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                     RemoveAssignedPatient.Callback callback, UserRepo users,
                                     CareProvider careProvider, Patient patient) 
    {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.callback = callback;
        this.users = users;
        this.careProvider = careProvider;
        this.patient = patient;
    }

    @Override
    public void run() {
        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){
        
            @Override
            public void run() {
                callback.onRAPFail();
            }
        });
    }
}