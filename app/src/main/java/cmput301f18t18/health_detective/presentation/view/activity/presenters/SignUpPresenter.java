package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import cmput301f18t18.health_detective.domain.interactors.CreateUserProfileContract;
import cmput301f18t18.health_detective.domain.model.User;

public class SignUpPresenter implements CreateUserProfileContract.Callback {

    String userName;
    String userEmail;
    String userPhoneNum;
    Boolean userType;

    public SignUpPresenter(String userName, String userEmail, String userPhoneNum, Boolean userType) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNum = userPhoneNum;
        this.userType = userType;
    }

    public void createNewUser(String userName, String userEmail, String userPhoneNum, Boolean userType){

    }

    @Override
    public void onCUPSuccess(User user) {

    }

    @Override
    public void onCUPFail() {

    }
}
