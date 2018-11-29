package cmput301f18t18.health_detective.domain.model.context.tree;

import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.PatientContext;
import cmput301f18t18.health_detective.domain.model.images.BodyImage;

public class ContextTreeParser {

    private ContextTree tree;

    public ContextTreeParser(ContextTree tree) {
        this.tree = tree;
    }

    public User getLoggedInUser() {
        // Start at the tail
        ContextTreeComponent treeComponent = tree.getTail();

        // Step through context tree until first user context is found, ideally it should be the first one
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof CareProviderContext) {
                return ((CareProviderContext) treeComponent).getCareProvider();
            }
            else if (treeComponent instanceof PatientContext) {
                return ((PatientContext) treeComponent).getPatient();
            }

            treeComponent = treeComponent.stepForward();
        }
    }

    public User getCurrentUserContext() {

        return null;
    }

    public Problem getCurrentProblemContext() {
        return null;
    }

    public Record getCurrentRecordContext() {
        return null;
    }

    public BodyLocation getCurrentBodyLocation() {
        return null;
    }

    public BodyImage getCurrentBodyPhoto() {
        return null;
    }
}
