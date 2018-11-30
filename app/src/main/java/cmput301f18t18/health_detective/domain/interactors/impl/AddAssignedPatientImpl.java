package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The AddAssignedPatientImpl class is a class intended to handle the adding of
 * patients to a care provider on the back end.
 */
public class AddAssignedPatientImpl extends AbstractInteractor implements AddAssignedPatient {

    private AddAssignedPatient.Callback callback;
    private String patientId;

    /**
     * Constructor for AddAssignedPatientImpl
     * @param callback
     * @param careProvider the care provider that the patient is being assigned to
     * @param patientId the Id of the patient being assigned
     */
    public AddAssignedPatientImpl(AddAssignedPatient.Callback callback,
                                  CareProvider careProvider, String patientId)
    {
        super();
        this.callback = callback;
        this.patientId = patientId;
    }

    /**
     * Adds the patient to the careprovider's list of assigned patients and then updates both
     * in the database
     *
     * Callbacks:
     *      -Calls onAAPNotValidUserId()
     *          if Id of patient being added is not valid
     *
     *      -Calls onAAPPatientAlreadyAssigned()
     *          if patient has already been assigned to the care provider
     *
     *      -Calls onAAPPatientDoesNotExist()
     *          if patient that wants to be added does not exist
     *
     *      -Calls onAAPSuccess()
     *          if the addition was successful, passing the added patient for context
     */
    @Override
    public void run() {
        final UserRepo userRepo = this.context.getUserRepo();
        final CareProvider careProvider;
        Patient patientToAdd;

        // Get the CareProvider to add the patient to (Should be the logged in user)
        ContextTreeParser contextTreeParser = new ContextTreeParser(this.context.getContextTree());
        User loggedInUser = contextTreeParser.getLoggedInUser();

        if (loggedInUser instanceof CareProvider)
            careProvider = (CareProvider)loggedInUser;
        else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onAAPLoggedInUserNotACareProvider();
                }
            });

            return;
        }

        // UserId invalid
        if (!User.isValidUserId(patientId)) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onAAPNotValidUserId();
                }
            });

            return;
        }

        // Patient already assigned
        if (careProvider.hasPatient(patientId)) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onAAPPatientAlreadyAssigned();
                }
            });

            return;
        }

        patientToAdd = userRepo.retrievePatientById(patientId);

        // Patient does not exist
        if (patientToAdd == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onAAPPatientDoesNotExist();
                }
            });

            return;
        }

        // Add patient to careProvider and add to repo
        careProvider.addPatient(patientToAdd);
        userRepo.updateUser(careProvider);

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onAAPSuccess();
            }
        });
    }
}