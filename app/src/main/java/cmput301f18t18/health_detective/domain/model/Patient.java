package cmput301f18t18.health_detective.domain.model;

import java.util.HashMap;

public class Patient extends User {

    private HashMap<Integer, Problem> problems;

    public  Patient() {
        super();
        this.problems = new HashMap<>();
    }

    public Patient(String userId) {
        super(userId);
        this.problems = new HashMap<>();
    }

    public Patient(String userId, String phoneNumber, String emailAddress) {
        super(userId, phoneNumber, emailAddress);
        this.problems = new HashMap<>();
    }

    public Problem getProblem(Integer problemId) {
        return problems.get(problemId);
    }

    public void addProblem(Problem problem) {
        problems.put(problem.getproblemID(), problem);
    }
}
