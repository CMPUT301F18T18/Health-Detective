package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class ProblemContext extends AbstractContextTreeComponent {

    private final Problem problem;

    public ProblemContext(Class<? extends AbstractInteractor> commandContext, Problem problem) {
        super(commandContext);
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }
}
