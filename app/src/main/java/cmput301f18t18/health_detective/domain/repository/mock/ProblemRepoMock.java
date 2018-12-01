package cmput301f18t18.health_detective.domain.repository.mock;

import java.util.ArrayList;
import java.util.HashMap;

import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;


public class ProblemRepoMock implements ProblemRepo {
    private HashMap<String, Problem> problems = new HashMap<>();

    @Override
    public void insertProblem(Problem problem) {
        if (problem == null) {
            return;
        }

        this.problems.put(problem.getProblemId(), problem);
    }

    @Override
    public void updateProblem(Problem problem) {

    }

    @Override
    public Problem retrieveProblemById(String problemID) {
        if (problemID == null) {
            return null;
        }

        return this.problems.get(problemID);    }

    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<String> problemIDs) {
        ArrayList<Problem> problemList = new ArrayList<>();

        if (problemIDs == null || problemIDs.isEmpty()) {
            return problemList;
        }

        for (Problem problem: this.problems.values()) {
            problemList.add(problem);
        }

        return problemList;
    }

    @Override
    public void deleteProblem(Problem problem) {
        if (problem == null) {
            return;
        }

        this.problems.remove(problem.getProblemId());
    }
}
