package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.interactors.ContextChange;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.context.tree.base.ContextTree;

public class ContextOnBack extends AbstractInteractor implements ContextChange {

    public ContextOnBack() {}

    @Override
    public void run() {
        final ContextTree contextTree = context.getContextTree();

        // Discard returned value we don't care
        contextTree.pop();
    }

    @Override
    public void execute() {
        this.isExecuting = true;
        this.run();
    }
}
