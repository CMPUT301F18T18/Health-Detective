package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class UserContext extends AbstractContextTreeComponent {
    private final User user;

    public UserContext(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
