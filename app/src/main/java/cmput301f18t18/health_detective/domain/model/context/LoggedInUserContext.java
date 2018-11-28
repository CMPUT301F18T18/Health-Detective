package cmput301f18t18.health_detective.domain.model.context;

import cmput301f18t18.health_detective.domain.model.User;

public class LoggedInUserContext {

    private User loggedInUser;

    public class Builder {

        // Required
        private User loggedInUser;

        // Optional

        public Builder(User loggedInUser) {
            this.loggedInUser = loggedInUser;
        }

        public LoggedInUserContext build(Builder builder) {
            return new LoggedInUserContext(this);
        }
    }

    private LoggedInUserContext(Builder builder) {
        this.loggedInUser = builder.loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
