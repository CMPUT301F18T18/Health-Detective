package cmput301f18t18.health_detective.model.domain;

public interface UserRepo {
    User getUser(String uuid);
    boolean saveUser(User usr);
    void deleteUser(String uuid);
}
