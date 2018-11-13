package cmput301f18t18.health_detective.domain.model;

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
}
