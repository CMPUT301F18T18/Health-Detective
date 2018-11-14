package cmput301f18t18.health_detective.domain.model;

public abstract class User {
    private String userId;
    private String phoneNumber;
    private String emailAddress;

    public static boolean isValidUserId(String userId) {

        if (userId.matches("\\w{8,}")) {
            return true;
        }

        return false;
    }

    public User() {
        this.setUserId(null);
        this.setPhoneNumber(null);
        this.setEmailAddress(null);
    }

    public User(String userId) {
        this.setUserId(userId);
        this.setPhoneNumber(null);
        this.setEmailAddress(null);
    }

    public User(String userId, String phoneNumber, String emailAddress) {
        this.setUserId(userId);
        this.setPhoneNumber(phoneNumber);
        this.setEmailAddress(emailAddress);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userID) {
        this.userId = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof User))
            return false;

        User usr = (User) o;
        return (this.userId.equals(usr.getUserId()));
    }

}
