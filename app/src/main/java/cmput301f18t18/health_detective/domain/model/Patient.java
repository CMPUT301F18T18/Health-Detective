package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class to store info specific to patients, extending all info that is in user.
 */
public class Patient extends User {
    private static final long serialVersionUID = 4L;

    private HashSet<String> problemIds = new HashSet<>();

    public Patient() {
        super();
    }

    public Patient(String userId) {
        super(userId);
    }

    public Patient(String userId, String phoneNumber, String emailAddress) {
        super(userId, phoneNumber, emailAddress);
    }

    public void addProblem(Problem problem) {
        problemIds.add(problem.getProblemId());
    }

    public void addProblem(String problemID) { problemIds.add(problemID); }

    public void removeProblem(Problem problem) {
        problemIds.remove(problem.getProblemId());
    }

    public void removeProblem(String problemId) { problemIds.remove(problemId); }

    public boolean isProblemsEmpty() {
        return problemIds.isEmpty();
    }

    public ArrayList<String> getProblemIds() {
        ArrayList<String> problems = new ArrayList<>();

        if (this.isProblemsEmpty()) {
            return problems;
        }

        for (String problemId: this.problemIds) {
            problems.add(problemId);
        }

        return problems;
    }
}
