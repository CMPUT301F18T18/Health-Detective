package cmput301f18t18.health_detective.domain.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class DomainContext {
    private static DomainContext ourInstance = null;

    private SecureRandom secureRandom;
    private User loggedInUser;
    private Patient patientContext;
    private Problem problemContext;
    private Record recordContext;

    public static DomainContext getInstance() {
        if (ourInstance == null) {
            ourInstance = new DomainContext();
        }

        return ourInstance;
    }

    private DomainContext() {
        try {
            this.secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    };

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public  SecureRandom getSecureRandom() {
        return this.secureRandom;
    }
}
