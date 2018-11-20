package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditUserProfile;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class EditUserProfileImpl extends AbstractInteractor implements EditUserProfile {

    private EditUserProfile.Callback callback;
    private UserRepo userRepo;
    private User userToEdit;
    private String email;
    private String phoneNumber;

    /**
     * Constructor for EditUserProfileImpl
     * @param userRepo the repository where users are stored
     * @param userToEdit the user profile that is being edited
     * @param email the email of the user profile that is being edited
     * @param phoneNumber the phone number of the user profile being edited
     */
    public EditUserProfileImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                               EditUserProfile.Callback callback, UserRepo userRepo,
                               User userToEdit, String email, String phoneNumber)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.userToEdit = userToEdit;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Main run method for EditUserProfileImpl. This method contains all the specific
     * business logic needed for the interactor. The main jobs of this method are to
     * make sure the email for the user profile is valid as well as the phone number,
     * and then to correctly edit the user profile with this information.
     */
    @Override
    public void run() {

        // Check if new email is valid
        if (!User.isValidEmailAddress(this.email)) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onEUPInvalidEmail();
                }
            });
        }

        // Check if new phone number is valid
        if (!User.isValidPhoneNumber(this.phoneNumber)) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onEUPInvaildPhoneNumber();
                }
            });
        }

        // Entered information is correct
        this.userToEdit.setEmailAddress(this.email);
        this.userToEdit.setPhoneNumber(this.phoneNumber);
        this.userRepo.updateUser(userToEdit);

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onEUPSuccess(userToEdit);
            }
        });
    }
}
