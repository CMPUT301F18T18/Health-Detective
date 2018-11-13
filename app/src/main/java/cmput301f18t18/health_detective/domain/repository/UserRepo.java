package cmput301f18t18.health_detective.domain.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;

public interface UserRepo {
    void insertUser(User user);
    void updateUser(User user);
    User retrieveUserById(String userID);
    ArrayList<User> retrieveUsersById(ArrayList<String> userID);
    void deleteUser(User user);
    void validateUserIdUniqueness(String userId);
}
