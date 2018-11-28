package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The CreateUserProfileImpl class is a class intended to handle the creation of user profiles
 * on the back end.
 */
public class CreateUserProfileImpl extends AbstractInteractor implements CreateUserProfile {

    private CreateUserProfile.Callback callback;
    private String userId;
    private String email;
    private String phoneNumber;
    private boolean isCareProvider;

    /**
     * Constructor for CreateUserProfileImpl
     * @param callback
     * @param userId the Id that the user has chosen to use
     * @param email the email that the user has chosen to use
     * @param phoneNumber the phone number that the user has chosen to use
     * @param isCareProvider check to see whethere you are a patient or care provider
     */
    public CreateUserProfileImpl(CreateUserProfile.Callback callback,
                                 String userId, String email, String phoneNumber, boolean isCareProvider)
    {
        super();
        this.callback = callback;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isCareProvider = isCareProvider;
    }

    /**
     * Creates a user profile and adds it to the database
     *
     * Callbacks:
     *      -Calls onCUPInvalidID()
     *          Id entered for creation is not valid
     *
     *      -Calls onCUPInvalidEmail()
     *          email entered for creation is not valid
     *
     *      -Calls onCUPInvalidPhoneNumber()
     *          phone number entered for creation is not valid
     *
     *      -Calls onCUPCareProviderSuccess(newCP)
     *          user profile was created successfully as a care provider and added to
     *          the database
     *
     *      -Calls onCUPPatientSuccess(newPatient)
     *          user profile was created successfuly as patient and added to the database
     */
    @Override
    public void run() {
        final UserRepo userRepo = this.context.getUserRepo();

        //Not a valid ID
        if (!User.isValidUserId(userId) || !userRepo.validateUserIdUniqueness(userId)){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPInvalidID();
                }
            });

            return;
        }

        //Not a valid Email address
        if (!User.isValidEmailAddress(this.email)){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPInvalidEmail();
                }
            });

            return;
        }

        //Not a valid Phone number
        if (!User.isValidPhoneNumber(this.phoneNumber)){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPInvalidPhoneNumber();
                }
            });

            return;
        }

        //Create new Care Provider profile
        if (isCareProvider == true) {
            CareProvider newCP = new CareProvider(userId,phoneNumber,email);
            userRepo.insertUser(newCP);
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPCareProviderSuccess(newCP);
                }
            });
        } else {
            //Create new Patient profile
            Patient newPatient = new Patient(userId,phoneNumber,email);
            userRepo.insertUser(newPatient);
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPPatientSuccess(newPatient);
                }
            });
        }
    }
}
