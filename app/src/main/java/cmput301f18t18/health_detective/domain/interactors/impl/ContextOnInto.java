package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.ContextChange;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.context.tree.base.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;

public class ContextOnInto extends AbstractInteractor implements ContextChange {

    private final Object contextData;

    public ContextOnInto(Object contextData) {
        this.contextData = contextData;

    }

    @Override
    public void run() {
        ContextTree contextTree = context.getContextTree();
        ContextTreeComponent contextTreeComponent = ContextTreeComponentFactory.getContextComponent(contextData);

        if (contextTreeComponent == null)
            return;

        contextTree.push(contextTreeComponent);
    }

    @Override
    public void execute() {
        this.isExecuting = true;
        this.run();
    }
}
