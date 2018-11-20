package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.GetAssignedPatients;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class GetAssignedPatientsImpl extends AbstractInteractor implements GetAssignedPatients {

    private GetAssignedPatients.Callback callback;
    private UserRepo userRepo;
    private CareProvider careProvider;

    public GetAssignedPatientsImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                   GetAssignedPatients.Callback callback, UserRepo userRepo,
                                   CareProvider careProvider)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.careProvider = careProvider;
    }

    /**
     * Queries the database for the provided careprovider's patients, not sorted
     *
     * Callbacks:
     *      - onGAPNoPatients()
     *          If careprovider has no records
     *
     *      - onGAPSuccess(ArrayList<Patient> patients)
     *          If query was successful, passing the problems patients as an argument
     */
    @Override
    public void run() {

        if (careProvider.isPatientsEmpty()) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onGAPNoPatients();
                }
            });

            return;
        }

        ArrayList<String> patientIds = careProvider.getPatientIds();
        ArrayList<Patient> patients =  this.userRepo.retrievePatientsById(patientIds);

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGAPSuccess(patients);
            }
        });
    }
}