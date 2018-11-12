package cmput301f18t18.health_detective.domain.model;

import java.util.HashMap;

public class Patient extends User {

    private HashMap<Integer, Problem> problems;

    public Problem getProblem(Integer problemId) {
        return problems.get(problemId);
    }

    public void addProblem(Problem problem) {
        problems.put(problem.getproblemID(), problem);
    }
}
