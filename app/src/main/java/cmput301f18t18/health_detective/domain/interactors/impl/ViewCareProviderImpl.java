package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.ViewCareProvider;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The GetAssignedPatientImpl class is a class intended to handle the retrieval of
 * a care provider's patient list.
 */
public class ViewCareProviderImpl extends AbstractInteractor implements ViewCareProvider {

    private Callback callback;

    /**
     * Constructor for GetAssignedPatientImpl
     * @param callback
     */
    public ViewCareProviderImpl(ViewCareProvider.Callback callback)
    {
        this.callback = callback;
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
        if (callback == null)
            return;

        final CareProvider careProvider;
        final UserRepo userRepo = this.context.getUserRepo();
        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        User user = treeParser.getCurrentUserContext();

        if (!(user instanceof CareProvider)) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVCPNoContext();
                }
            });

            return;
        }

        careProvider = (CareProvider) user;
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onVCPSuccessDetails(careProvider.getUserId(), careProvider.getEmailAddress(), careProvider.getPhoneNumber());
            }
        });


        if (careProvider.isPatientsEmpty()) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onVCPNoPatients();
                }
            });

            return;
        }

        ArrayList<String> patientIds = careProvider.getPatientIds();
        ArrayList<Patient> patients =  userRepo.retrievePatientsById(patientIds);

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onVCPSuccess(patients);
            }
        });
    }
}