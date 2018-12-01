package cmput301f18t18.health_detective.domain.model.context.tree;

import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.impl.BodyLocationContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.PatientContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.ProblemContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.RecordContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.UserContext;

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
            else if (treeComponent instanceof UserContext) {
                return ((UserContext) treeComponent).getUser();
            }

            treeComponent = treeComponent.prev();
        }
    }

    public User getCurrentUserContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Step through until first User is found
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof UserContext) {
                return ((UserContext) treeComponent).getUser();
            }
            else if (treeComponent instanceof CareProviderContext) {
                return ((CareProviderContext) treeComponent).getCareProvider();
            }
            else if (treeComponent instanceof PatientContext) {
                return ((PatientContext) treeComponent).getPatient();
            }

            treeComponent = treeComponent.next();
        }
    }

    public Patient getCurrentPatientContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Step through until first Patient is found
        while (true) {
            if (treeComponent == null)
                return null;

            else if (treeComponent instanceof UserContext) {
                User user = ((UserContext) treeComponent).getUser();

                if (user instanceof Patient)
                    return (Patient) user;
            }
            else if (treeComponent instanceof PatientContext) {
                return ((PatientContext) treeComponent).getPatient();
            }

            treeComponent = treeComponent.next();
        }
    }

    public Problem getCurrentProblemContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Needs a Patient context to exist
        if (getCurrentPatientContext() == null)
            return null;

        // Step through until first Problem is found
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof PatientContext) {
                // If patient found first, no problem in scope
                return null;
            }
            else if (treeComponent instanceof ProblemContext) {
                return ((ProblemContext) treeComponent).getProblem();
            }

            treeComponent = treeComponent.next();
        }
    }

    public Record getCurrentRecordContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Needs a Problem context to exist
        if (getCurrentProblemContext() == null)
            return null;

        // Step through until first Record is found
        while (true) {
            if (treeComponent == null)
                return null;
            if (treeComponent instanceof ProblemContext) {
                // If problem is found first no record in scope
                return null;
            }
            else if (treeComponent instanceof RecordContext) {
                return ((RecordContext) treeComponent).getRecord();
            }

            treeComponent = treeComponent.next();
        }
    }

    public BodyLocation getCurrentBodyLocationContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Needs a Patient context to exist
        if (getCurrentPatientContext() == null)
            return null;

        // Step through until first BodyLocation is found
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof BodyLocationContext) {
                return ((BodyLocationContext) treeComponent).getBodyLocation();
            }

            treeComponent = treeComponent.next();
        }
    }
}
