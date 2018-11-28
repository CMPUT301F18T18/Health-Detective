package cmput301f18t18.health_detective.domain.model.context.tree.base;

import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;

public class ContextTree {

    private ContextTreeComponent head;

    public ContextTree() {}

    public void push(ContextTreeComponent context) {
        ContextTreeComponent parent = null;

        if (head != null) {
            parent = head;
        }

        if (parent != null) {
            parent.setChild(context);
        }

        context.setParent(parent);
        context.setChild(null);

        head = context;
    }

    public ContextTreeComponent pop() {
        ContextTreeComponent poppedComponent;

        // Tree Empty
        if (head == null)
            return null;

        // Tree not empty get heads child
        poppedComponent = head;
        head = head.stepBackward();

        // popped value not last item in tree
        if (head != null) {
            head.setChild(null);
        }

        return poppedComponent;
    }
}
