package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.HashSet;

public class Patient extends User {

    private HashSet<Integer> problemIds;

    public Patient() {
        super();
        this.problemIds = new HashSet<>();
    }

    public Patient(String userId) {
        super(userId);
        this.problemIds = new HashSet<>();
    }

    public Patient(String userId, String phoneNumber, String emailAddress) {
        super(userId, phoneNumber, emailAddress);
        this.problemIds = new HashSet<>();
    }

    public void addProblem(Problem problem) {
        problemIds.add(problem.getProblemID());
    }

    public void addProblem(Integer problemID) { problemIds.add(problemID); }

    public boolean isProblemsEmpty() {
        return problemIds.isEmpty();
    }

    public ArrayList<Integer> getProblemIds() {
        ArrayList<Integer> problems = new ArrayList<>();

        if (this.isProblemsEmpty()) {
            return problems;
        }

        for (Integer problemId: this.problemIds) {
            problems.add(problemId);
        }

        return  problems;
    }
}
