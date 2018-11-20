package cmput301f18t18.health_detective.domain.model;

import java.io.Serializable;

/**
 * The base user class which is extended by the specific user types.
 * Everything entity can be related back to user through one way or another
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 3L;
    private String userId;
    private String phoneNumber;
    private String emailAddress;

    /**
     * Compares given string to rules for a valid userId
     *
     * @param userId String to check validity
     * @return       True if valid
     */
    public static boolean isValidUserId(String userId) {

        if (userId == null) {
            return false;
        }

        if (!userId.matches("\\w{8,}")) {
            return false;
        }

        return true;
    }

    /**
     * Compares given string to rules for a valid phone number
     * @param phoneNumber String to check validity
     * @return            True if valid
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }

        String phoneNumberRegEx = "\\(\\d{3}\\) \\d{3}-\\d{4}";

        if (!phoneNumber.matches(phoneNumberRegEx)) {
            return  false;
        }

        return true;
    }

    /**
     * Compares given string to rules for a valid email
     * @param emailAddress String to check validity
     * @return            True if valid
     */
    public static boolean isValidEmailAddress(String emailAddress) {
        if (emailAddress == null) {
            return  false;
        }

        String emailAddressRegEx = "\\w{1,}([\\.]{1}[\\w-]{1,}){0,}@\\w{1,}([\\.]{1}[\\w-]{1,}){0,}";

        if (!emailAddress.matches(emailAddressRegEx)){
            return false;
        }

        return true;
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
