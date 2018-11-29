package cmput301f18t18.health_detective.domain.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void ValidUserIdTest1() {
        String userId = "abcdefgh";

        assertTrue(User.isValidUserId(userId));
    }

    @Test
    public void ValidUserIdTest2() {
        String userId = "Testing_That_Underscores_Caps_And_Long_Userids_Are_Valid";

        assertTrue(User.isValidUserId(userId));
    }

    @Test
    public void InvalidUserIdTest_Apostrophes() {
        String userId = "HelloThisIsUser's";

        assertFalse(User.isValidUserId(userId));
    }

    @Test
    public void InvalidUserIdTest_EmptyString() {
        String userId = "";

        assertFalse(User.isValidUserId(userId));
    }

    @Test
    public void InvalidUserIdTest_Null() {
        String userId = null;

        assertFalse(User.isValidUserId(userId));
    }

    @Test
    public void InvalidUserIdTest_LessThanEightCharacters() {
        String userId = "abcdefg";

        assertFalse(User.isValidUserId(userId));
    }

    @Test
    public void InvalidUserIdTest_Spaces() {
        String userId = "Testing Spaces";

        assertFalse(User.isValidUserId(userId));
    }

    @Test
    public void InvalidUserIdTest_NewLine() {
        String userId = "Testing\nSpaces";

        assertFalse(User.isValidUserId(userId));
    }

    @Test
    public void InvalidUserIdTest_Tabs() {
        String userId = "Testing\tSpaces";

        assertFalse(User.isValidUserId(userId));
    }

    @Test
    public void ValidPhoneNumber() {
        String phoneNumber = "(888) 123-4567";

        assertTrue(User.isValidPhoneNumber(phoneNumber));
    }

    @Test
    public void InvalidPhoneNumber_MissingDash() {
        String phoneNumber = "(888) 1234567";

        assertFalse(User.isValidPhoneNumber(phoneNumber));
    }

    @Test
    public void InvalidPhoneNumber_MissingBracket() {
        String phoneNumber = "(888 123-4567";

        assertFalse(User.isValidPhoneNumber(phoneNumber));
    }

    @Test
    public void InvalidPhoneNumber_MissingSpace() {
        String phoneNumber = "(888)123-4567";

        assertFalse(User.isValidPhoneNumber(phoneNumber));
    }

    @Test
    public void InvalidPhoneNumber_Null() {
        String phoneNumber = null;

        assertFalse(User.isValidPhoneNumber(phoneNumber));
    }

    @Test
    public void InvalidPhoneNumber_EmptyString() {
        String phoneNumber = "";

        assertFalse(User.isValidPhoneNumber(phoneNumber));
    }

    @Test
    public void isValidEmailAddress() {
        String email = "test.test@test.test";

        assertTrue(User.isValidEmailAddress(email));
    }

    @Test
    public void isValidEmailAddress2() {
        String email = "test@test.test";

        assertTrue(User.isValidEmailAddress(email));
    }

    @Test
    public void isValidEmailAddress3() {
        String email = "test.test.test.test.test.test.test.test.test.test@test.test.test.test.est.test.test";

        assertTrue(User.isValidEmailAddress(email));
    }

    @Test
    public void InvalidEmailAddress_StartingDot() {
        String email = ".test@test.test";


        assertFalse(User.isValidEmailAddress(email));
    }

    @Test
    public void InvalidEmailAddress_DoubleDot() {
        String email = "test..test@test.test";


        assertFalse(User.isValidEmailAddress(email));
    }

    @Test
    public void InvalidEmailAddress_DoubleDot2() {
        String email = "test.test@test..test";


        assertFalse(User.isValidEmailAddress(email));
    }

    @Test
    public void InvalidEmailAddress_NoAt() {
        String email = "test.testtest.test";


        assertFalse(User.isValidEmailAddress(email));
    }

    @Test
    public void InvalidEmailAddress_Null() {
        String email = null;


        assertFalse(User.isValidEmailAddress(email));
    }

    @Test
    public void InvalidEmailAddress_EmptyString() {
        String email = "";


        assertFalse(User.isValidEmailAddress(email));
    }
}