package cmput301f18t18.health_detective.domain.model.context.tree;

import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.impl.BodyImageContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.BodyLocationContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.PatientContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.ProblemContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.RecordContext;
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

    public Patient getCurrentPatientContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Step through until first User is found
        while (true) {
            if (treeComponent == null)
                return null;

            else if (treeComponent instanceof PatientContext) {
                return ((PatientContext) treeComponent).getPatient();
            }

            treeComponent = treeComponent.stepBackward();
        }
    }

    public Problem getCurrentProblemContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Step through until first User is found
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof ProblemContext) {
                return ((ProblemContext) treeComponent).getProblem();
            }

            treeComponent = treeComponent.stepBackward();
        }
    }

    public Record getCurrentRecordContext() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Step through until first User is found
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof ProblemContext) {
                return ((RecordContext) treeComponent).getRecord();
            }

            treeComponent = treeComponent.stepBackward();
        }
    }

    public BodyLocation getCurrentBodyLocation() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Step through until first User is found
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof ProblemContext) {
                return ((BodyLocationContext) treeComponent).getBodyLocation();
            }

            treeComponent = treeComponent.stepBackward();
        }
    }

    public BodyImage getCurrentBodyPhoto() {
        // Start at the head
        ContextTreeComponent treeComponent = tree.getHead();

        // Step through until first User is found
        while (true) {
            if (treeComponent == null)
                return null;
            else if (treeComponent instanceof ProblemContext) {
                return ((BodyImageContext) treeComponent).getBodyImage();
            }

            treeComponent = treeComponent.stepBackward();
        }
    }
}
