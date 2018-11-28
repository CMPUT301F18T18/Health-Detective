package cmput301f18t18.health_detective.domain.model.context.component;

public interface ContextTreeComponent extends Cloneable {
    ContextTreeComponent stepForward();
    ContextTreeComponent stepBackward();
    void setChild(ContextTreeComponent child);
    void setParent(ContextTreeComponent parent);
}
