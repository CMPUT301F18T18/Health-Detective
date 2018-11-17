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
    private UserRepo userRepo;
    private CareProvider careProvider;
    private Patient patient;

    public RemoveAssignedPatientImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                     RemoveAssignedPatient.Callback callback, UserRepo userRepo,
                                     CareProvider careProvider, Patient patient) 
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.careProvider = careProvider;
        this.patient = patient;
    }

    @Override
    public void run() {

        if (!careProvider.hasPatient(patient)) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onRAPPatientNotAssigned();
                }
            });

            return;
        }

        careProvider.removePatient(patient);
        userRepo.updateUser(careProvider);

        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){
        
            @Override
            public void run() {
                callback.onRAPSuccess(patient);
            }
        });
    }
}