package cmput301f18t18.health_detective.domain.model.context.component.base;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;

public class AbstractContextTreeComponent implements ContextTreeComponent, Cloneable{

    private final Class<? extends AbstractInteractor> commandType;
    private ContextTreeComponent child;
    private ContextTreeComponent parent;


    public AbstractContextTreeComponent(Class<? extends AbstractInteractor> commandContext) {
        this.parent = null;
        this.child = null;
        this.commandType = commandContext;
    }

    @Override
    public ContextTreeComponent stepForward() {
        return child;
    }

    @Override
    public ContextTreeComponent stepBackward() {
        return parent;
    }

    @Override
    public void setChild(ContextTreeComponent child) {
        this.child = child;
    }

    @Override
    public void setParent(ContextTreeComponent parent) {
        this.parent = parent;
    }

    @Override
    public Class<? extends AbstractInteractor> getCommandContext() {
        return commandType;
    }
}
