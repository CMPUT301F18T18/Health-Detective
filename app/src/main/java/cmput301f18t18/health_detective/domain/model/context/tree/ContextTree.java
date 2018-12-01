package cmput301f18t18.health_detective.domain.model.context.tree;

import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;

public class ContextTree {

    private ContextTreeComponent head;
    private ContextTreeComponent tail;

    public ContextTree() {}

    public ContextTreeComponent getHead() {
        return head;
    }

    public ContextTreeComponent getTail() {
        return tail;
    }

    public synchronized void push(ContextTreeComponent context) {
        ContextTreeComponent parent;

        if (context == null)
            return;

        // If tree is empty add to tree set head and tail to point at it
        if (head == null) {
            head = context;
            tail = context;
            context.setChild(null);
            context.setParent(null);

            return;
        }

        parent = head;
        head = context;

        parent.setChild(head);
        context.setParent(parent);
        context.setChild(null);
    }

    public synchronized ContextTreeComponent pop() {
        ContextTreeComponent poppedComponent;

        // Tree Empty
        if (head == null)
            return null;

        // Tree not empty get heads child
        poppedComponent = head;
        head = head.stepBackward();

        // popped value last item in tree
        if (head == null) {
            tail = null;
        }

        head.setChild(null);

        return poppedComponent;
    }
}
