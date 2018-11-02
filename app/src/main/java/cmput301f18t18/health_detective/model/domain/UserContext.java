package cmput301f18t18.health_detective.model.domain;


public class UserContext {
    private static final UserContext ourInstance = new UserContext();

    boolean isCareProvider;
    User user;

    public static UserContext getInstance() {
        return ourInstance;
    }

    private UserContext() {
    }
}
