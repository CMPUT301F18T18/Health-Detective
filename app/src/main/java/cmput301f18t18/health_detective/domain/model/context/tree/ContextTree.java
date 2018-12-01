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

        if (context == null)
            return;

        context.setNext(head);
        context.setPrev(null);

        if (head != null)
            head.setPrev(context);

        if (tail == null)
            tail = context;

        head = context;

        removeDuplicateTypes(context.next(), context);
    }

    private void removeDuplicateTypes(ContextTreeComponent contextToCheck, ContextTreeComponent reference) {
        // Leaf Reached
        if (contextToCheck == null)
            return;

        if (contextToCheck.getClass().equals(reference.getClass())) {
            remove(contextToCheck);
        }

        removeDuplicateTypes(contextToCheck.next(), reference);
    }

    private void remove(ContextTreeComponent context) {
        ContextTreeComponent prev = context.prev();
        ContextTreeComponent next = context.next();

        if (prev != null)
            prev.setNext(next);

        if (next != null)
            next.setPrev(prev);
    }
}
