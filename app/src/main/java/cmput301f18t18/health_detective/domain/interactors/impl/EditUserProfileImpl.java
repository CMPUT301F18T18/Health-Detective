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
