package cmput301f18t18.health_detective.domain.repository.mock;

import java.util.ArrayList;
import java.util.HashMap;

import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class UserRepoMock implements UserRepo {

    private HashMap<String, User> users = new HashMap<>();


    @Override
    public void insertUser(User user) {
        this.users.put(user.getUserID(), user);
    }

    @Override
    public void updateUser(User user) {
        this.users.put(user.getUserID(), user);
    }

    @Override
    public User retrieveUserById(String userID) {
        return this.users.get(userID);
    }

    @Override
    public ArrayList<User> retrieveUsersById(ArrayList<String> userID) {
        ArrayList<User> userList = new ArrayList<>();

        for (User user: this.users.values()) {
            userList.add(user);
        }

        return userList;
    }

    @Override
    public void deleteUser(User user) {
        this.users.remove(user.getUserID());
    }
}