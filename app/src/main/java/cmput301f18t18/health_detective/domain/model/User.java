package cmput301f18t18.health_detective.domain.model;

public abstract class User {
    private String userID;
    private String phoneNumber;
    private String emailAddress;

    public User() {
        this.setUserID(null);
        this.setPhoneNumber(null);
        this.setEmailAddress(null);
    }

    public User(String userId) {
        this.setUserID(userId);
        this.setPhoneNumber(null);
        this.setEmailAddress(null);
    }

    public User(String userId, String phoneNumber, String emailAddress) {
        this.setUserID(userId);
        this.setPhoneNumber(phoneNumber);
        this.setEmailAddress(emailAddress);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
        return (this.userID.equals(usr.getUserID()));
    }

}
