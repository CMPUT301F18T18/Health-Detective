package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditUserProfile;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class EditUserProfileMockSuccess extends AbstractInteractor implements EditUserProfile {

    private EditUserProfile.Callback callback;
    private UserRepo userRepo;
    private User userToEdit;
    private String email;
    private String phoneNumber;

    public EditUserProfileMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                      EditUserProfile.Callback callback, UserRepo userRepo,
                                      User userToEdit, String email, String phoneNumber)
    {
        super();
        this.callback = callback;
        this.userRepo = userRepo;
        this.userToEdit = userToEdit;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    @Override
    public void run() {
        userToEdit.setPhoneNumber(phoneNumber);
        userToEdit.setEmailAddress(email);

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEUPSuccess(userToEdit);
            }
        });
    }
}
