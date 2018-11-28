package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.RemoveAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The RemoveAssignedPatientImpl class is a class intended to handle the removal of
 * patients from a care provider on the back end.
 */
public class RemoveAssignedPatientImpl extends AbstractInteractor implements RemoveAssignedPatient {

    private RemoveAssignedPatient.Callback callback;
    private UserRepo userRepo;
    private CareProvider careProvider;
    private Patient patient;

    /**
     * Creates a new RemoveAssignedPatient object from the provided parameters
     * @param callback
     * @param userRepo the repository where users are stored
     * @param careProvider the care provider who is having a pateint removed
     * @param patient the patient being removed from the care provider
     */
    public RemoveAssignedPatientImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                     RemoveAssignedPatient.Callback callback, UserRepo userRepo,
                                     CareProvider careProvider, Patient patient)
    {
        super();
        this.callback = callback;
        this.userRepo = userRepo;
        this.careProvider = careProvider;
        this.patient = patient;
    }

    /**
     * Removes the patient from the careproviders list of assigned patients and then updates both
     * in the database
     *
     * Callbacks:
     *      -Calls onRAPPatientNotAssigned()
     *          if patient is not assigned to the careprovider
     *
     *      -Calls onRAPSuccess(Patient patient)
     *          if the removal was successful, passing the removed patient for context
     */
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