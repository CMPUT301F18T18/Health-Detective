package cmput301f18t18.health_detective.domain.model.context.component.base;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;

public class AbstractContextTreeComponent implements ContextTreeComponent, Cloneable{

    private Class<? extends AbstractInteractor> commandType;
    private ContextTreeComponent next;
    private ContextTreeComponent prev;


    public AbstractContextTreeComponent() {
        this.prev = null;
        this.next = null;
    }

    @Override
    public ContextTreeComponent next() {
        return next;
    }

    @Override
    public ContextTreeComponent prev() {
        return prev;
    }

    @Override
    public void setNext(ContextTreeComponent next) {
        this.next = next;
    }

    @Override
    public void setPrev(ContextTreeComponent prev) {
        this.prev = prev;
    }

    @Override
    public Class<? extends AbstractInteractor> getCommandContext() {
        return commandType;
    }

    public void setCommandType(Class<? extends AbstractInteractor> commandType) {
        this.commandType = commandType;
    }
}
