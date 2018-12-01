package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;

public class PutContext extends AbstractInteractor {

    private Object o;

    public PutContext(Object o) {
        this.o = o;
    }

    @Override
    public void run() {
        ContextTree tree = context.getContextTree();
        ContextTreeComponent component = ContextTreeComponentFactory.getContextComponent(o, tree);

        if (component == null)
            return;

        tree.push(component);
    }

    @Override
    public void execute() {
        isExecuting = true;
        run();
    }

}
