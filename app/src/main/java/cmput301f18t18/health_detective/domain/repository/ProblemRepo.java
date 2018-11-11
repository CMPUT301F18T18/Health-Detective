package cmput301f18t18.health_detective.domain.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.Problem;

public interface ProblemRepo {
    void insertProblem(Problem problem);
    void updateProblem(Problem problem);
    Problem retrieveProblemById(Integer problemID);
    ArrayList<Problem> retrieveProblemsById(ArrayList<Integer> problemID);
    void deleteProblem(Problem problem);
}
