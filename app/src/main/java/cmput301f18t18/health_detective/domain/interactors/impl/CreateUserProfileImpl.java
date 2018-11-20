package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class CreateUserProfileImpl extends AbstractInteractor implements CreateUserProfile {

    private CreateUserProfile.Callback callback;
    private UserRepo userRepo;
    private String userId;
    private String email;
    private String phoneNumber;
    private boolean isCareProvider;

    /**
     * Constructor for CreateUserProfileImpl
     * @param userRepo the repository where users are stored
     * @param userId the Id that the user has chosen to use
     * @param email the email that the user has chosen to use
     * @param phoneNumber the phone number that the user has chosen to use
     * @param isCareProvider check to see whethere you are a patient or care provider
     */
    public CreateUserProfileImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                 CreateUserProfile.Callback callback, UserRepo userRepo,
                                 String userId, String email, String phoneNumber, boolean isCareProvider)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isCareProvider = isCareProvider;
    }

    /**
     * Main run method for CreateUserProfileImpl. This method contains all the specific
     * business logic needed for the interactor. The main jobs of this method are to
     * make sure the userId is valid, the email address provided is valid, the phone
     * number is valid, and then create the correct user as either a patient or a
     * care provider.
     */
    @Override
    public void run() {
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
