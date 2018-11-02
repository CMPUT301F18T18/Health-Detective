package cmput301f18t18.health_detective.model.domain;

import cmput301f18t18.health_detective.model.domain.Problem;

public interface ProblemRepo {
    Problem getProblem(int problemID);
    void removeProblem(int problemID);
    boolean saveProblem(Problem problem);
}
