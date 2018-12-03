package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.GetUser;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class GetUserImpl extends AbstractInteractor implements GetUser {

    private GetUser.Callback callback;
    private String userId;

    /**
     * Creates a new UserLoginImpl object from the provided parameters
     * @param callback
     * @param userId the Id of the user attempting to log in
     */
    public GetUserImpl(GetUser.Callback callback, String userId)
    {
        super();
        this.callback = callback;
        this.userId = userId;
    }

    /**
     * Using the userId string the command queries the database for a user with the same userId
     * returning it via the callbacks methods based on the type of user
     *      onLoginPatientSuccess(Patient patient)
     *      onLoginCareProviderSuccess(CareProvider careProvider)
     *
     * If the userId is an invalid format the command calls the callbacks
     *      onLoginInvalidUserId() method
     *
     * If the userId is not found  the command calls its callbacks
     *      onLoginUserDoesNotExist() method
     */
    @Override
    public void run() {
        final Patient patient;
        final CareProvider careProvider;
        final UserRepo userRepo = context.getUserRepo();

        if (!User.isValidUserId(userId)){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onGetUserInvalidUserId();
                }
            });

            return;
        }

        patient = userRepo.retrievePatientById(userId);

        if (patient != null) {


            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onGetUserPatientSuccess(patient);
                }
            });

            return;
        }

        careProvider = userRepo.retrieveCareProviderById(userId);

        if (careProvider != null) {


            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onGetUserProviderSuccess(careProvider);
                }
            });

            return;
        }

        // Could not find user
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGetUserDoesNotExist();
            }
        });
    }
}
