package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditUserProfile;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The EditUserProfileImpl class is a class intended to handle the editing of user
 * profiles on the back end.
 */
public class EditUserProfileImpl extends AbstractInteractor implements EditUserProfile {

    private EditUserProfile.Callback callback;
    private String email;
    private String phoneNumber;

    /**
     * Constructor for EditUserProfileImpl
     * @param callback
     * @param email the email of the user profile that is being edited
     * @param phoneNumber the phone number of the user profile being edited
     */
    public EditUserProfileImpl(EditUserProfile.Callback callback,
                               User userToEdit, String email, String phoneNumber)
    {
        super();
        this.callback = callback;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Edits the information contained in a user profile and updates the database.
     *
     * Callbacks:
     *      -Calls onEUPInvalidEmail()
     *          if email is invalid
     *
     *      -Calls onEUPInvaildPhoneNumber()
     *          if phone number is invalid
     *
     *      -Calls onEUPSuccess(userToEdit)
     *          if editing user is successful, updates everything containing user
     */
    @Override
    public void run() {
        final UserRepo userRepo = this.context.getUserRepo();
        final User userToEdit;

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

        ContextTree tree = this.context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        userToEdit = treeParser.getLoggedInUser();

        if (userToEdit != treeParser.getCurrentUserContext()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onEUPInvalidPermissions();
                }
            });
        }

        // Entered information is correct
        userToEdit.setEmailAddress(this.email);
        userToEdit.setPhoneNumber(this.phoneNumber);
        userRepo.updateUser(userToEdit);

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onEUPSuccess(userToEdit);
            }
        });
    }
}
