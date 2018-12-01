package cmput301f18t18.health_detective.domain.model.context.component;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

public interface ContextTreeComponent extends Cloneable {
    ContextTreeComponent next();
    ContextTreeComponent prev();
    void setNext(ContextTreeComponent next);
    void setPrev(ContextTreeComponent prev);
    Class<? extends AbstractInteractor> getCommandContext();
}
