package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;

public class GetLoggedInUserImpl extends AbstractInteractor implements GetLoggedInUser {

    private Callback callback;

    public GetLoggedInUserImpl(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        if (callback == null)
            return;

        final User loggedInUser;
        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        loggedInUser = treeParser.getLoggedInUser();

        if (loggedInUser == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onGLIUNoUserLoggedIn();
                }
            });

            return;
        }

        if (loggedInUser instanceof Patient) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onGLIUPatient((Patient) loggedInUser);
                }
            });
        }
        else if (loggedInUser instanceof CareProvider) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onGLIUCareProvider((CareProvider) loggedInUser);
                }
            });
        }

    }
}
