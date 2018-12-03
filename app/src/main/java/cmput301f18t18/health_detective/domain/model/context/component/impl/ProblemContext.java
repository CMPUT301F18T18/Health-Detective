package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;


/**
 * Problem context tree component, stores a problem in the context tree
 */
public class ProblemContext extends AbstractContextTreeComponent {

    private final Problem problem;

    public ProblemContext(Problem problem) {
        super();
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }
}
