package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class ProblemContext extends AbstractContextTreeComponent {

    private final Problem problem;

    public ProblemContext(Problem problem) {
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }
}