package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.RemoveAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The RemoveAssignedPatientImpl class is a class intended to handle the removal of
 * patients from a care provider on the back end.
 */
public class RemoveAssignedPatientImpl extends AbstractInteractor implements RemoveAssignedPatient {

    private RemoveAssignedPatient.Callback callback;
    private Patient patient;

    /**
     * Creates a new RemoveAssignedPatient object from the provided parameters
     * @param callback
     * @param careProvider the care provider who is having a pateint removed
     * @param patient the patient being removed from the care provider
     */
    public RemoveAssignedPatientImpl(RemoveAssignedPatient.Callback callback,
                                     CareProvider careProvider, Patient patient)
    {
        super();
        this.callback = callback;
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

        final UserRepo userRepo = this.context.getUserRepo();
        final CareProvider careProvider;

        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        User userContext = treeParser.getCurrentUserContext();

        if (!(userContext instanceof CareProvider)) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onRAPInvalidPermissions();
                }
            });
        }

        careProvider = (CareProvider) userContext;

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