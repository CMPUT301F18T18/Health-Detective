package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.*;

public class CreateUserProfileImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private CreateUserProfileMockPresenter callback;
    private UserRepo users;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.users = new UserRepoMock();
        this.callback = new CreateUserProfileMockPresenter();

    }

    // Testing patient is correctly created
    @Test
    public void testCUPPatient(){
        String goodId = "GoodIdGuy";
        String goodNumber = "(780) 222-4444";
        String goodEmail = "detective@ualberta.ca";

        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                goodEmail,
                goodNumber,
                false
        );
        command.execute();

        // Check to make sure user created is a patient (testing isCareProvider)
        assertTrue(callback.isCUPSuccessPatient());

        Patient patient = callback.getCreatedPatient();

        // Check to make sure all the attributes of the patient were correctly assigned
        assertNotNull(patient);
        assertEquals("GoodIdGuy", patient.getUserId());
        assertEquals("(780) 222-4444", patient.getPhoneNumber());
        assertEquals("detective@ualberta.ca", patient.getEmailAddress());
        assertFalse(callback.isCUPSuccessCareProvider());

        // Check repo has been updated
        assertEquals(users.retrievePatientById("GoodIdGuy").getUserId(), goodId);
    }

    // Testing care provider is correctly created
    @Test
    public void testCUPCareProvider() {
        String goodId = "GoodIdGirl";
        String goodNumber = "(780) 333-7777";
        String goodEmail = "health@ualberta.ca";

        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                goodEmail,
                goodNumber,
                true
        );
        command.execute();

        // Check to make sure user created is a care provider (testing isCareProvider)
        assertTrue(callback.isCUPSuccessCareProvider());

        CareProvider careProvider = callback.getCreatedCareProvider();

        // Check to make sure all the attributes of the care provider were correctly assigned
        assertNotNull(careProvider);
        assertEquals("GoodIdGirl", careProvider.getUserId());
        assertEquals("(780) 333-7777", careProvider.getPhoneNumber());
        assertEquals("health@ualberta.ca", careProvider.getEmailAddress());
        assertFalse(callback.isCUPSuccessPatient());

        // Check repo has been updated
        assertEquals(users.retrieveCareProviderById("GoodIdGirl").getUserId(), goodId);
    }

    // Testing blank userId
    @Test
    public void testCUPBlankUserId(){
        String emptyId = "";
        String goodEmail = "detective@ualberta.ca";
        String goodNumber = "(780) 333-1111";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                emptyId,
                goodEmail,
                goodNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidUserID());
    }

    // Testing userId that's too short
    @Test
    public void testCUPTooShortUserId(){
        String shortId = "Hello";
        String goodEmail = "detective@ualberta.ca";
        String goodNumber = "(780) 333-1111";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                shortId,
                goodEmail,
                goodNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidUserID());
    }

    // Testing userId containing invalid characters
    @Test
    public void testCUPInvalidCharsUserId(){
        String invalidId = "--THEMAN--";
        String goodEmail = "detective@ualberta.ca";
        String goodNumber = "(780) 333-1111";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                invalidId,
                goodEmail,
                goodNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidUserID());
    }

    // Testing blank phone number
    @Test
    public void testCUPBlankNumber(){
        String goodId = "GoodBoyId";
        String goodEmail = "healthd@ualberta.ca";
        String emptyNumber = "";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                goodEmail,
                emptyNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidPhoneNumber());
    }

    // Testing wrongly formatted phone number
    @Test
    public void testCUPBadFormatNumber(){
        String goodId = "GoodBoyId";
        String goodEmail = "healthd@ualberta.ca";
        String wrongNumber = "780 111 0000";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                goodEmail,
                wrongNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidPhoneNumber());
    }

    // Testing phone number containing invalid characters
    @Test
    public void testCUPInvalidCharsNumber(){
        String goodId = "GoodBoyId";
        String goodEmail = "healthd@ualberta.ca";
        String invalidNumber = "ABC---wxyz";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                goodEmail,
                invalidNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidPhoneNumber());
    }

    // Testing blank email address
    @Test
    public void testCUPBlankEmail(){
        String goodId = "GoodGuyId";
        String emptyEmail = "";
        String goodNumber = "(780) 333-7777";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                emptyEmail,
                goodNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidEmail());
    }

    // Testing email containing invalid characters/ wrongly formatted
    @Test
    public void testCUPInvalidEmail(){
        String goodId = "GoodGuyId";
        String invalidEmail = "jeffrey&hotmail,com";
        String goodNumber = "(780) 333-7777";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                invalidEmail,
                goodNumber,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidEmail());
    }
}

// Mock presenter to use for tests
class CreateUserProfileMockPresenter implements CreateUserProfile.Callback {

    private boolean CUPSuccessPatient = false;
    private boolean CUPSuccessCareProvider = false;
    private boolean invalidUserID = false;
    private boolean invalidEmail = false;
    private boolean invalidPhoneNumber = false;
    private Patient createdPatient = null;
    private CareProvider createdCareProvider = null;

    public boolean isCUPSuccessPatient() {
        return CUPSuccessPatient;
    }

    public boolean isCUPSuccessCareProvider() {
        return CUPSuccessCareProvider;
    }

    public boolean isCUPInvalidUserID() {
        return invalidUserID;
    }

    public boolean isCUPInvalidEmail() {
        return invalidEmail;
    }

    public boolean isCUPInvalidPhoneNumber() {
        return invalidPhoneNumber;
    }

    public Patient getCreatedPatient() {
        return createdPatient;
    }

    public CareProvider getCreatedCareProvider() {
        return createdCareProvider;
    }


    @Override
    public void onCUPPatientSuccess(Patient patient) {
        this.CUPSuccessPatient = true;
        this.createdPatient = patient;
    }

    @Override
    public void onCUPCareProviderSuccess(CareProvider careProvider) {
        this.CUPSuccessCareProvider = true;
        this.createdCareProvider = careProvider;
    }

    @Override
    public void onCUPInvalidID() {
        this.invalidUserID = true;
    }

    @Override
    public void onCUPInvalidEmail() {
        this.invalidEmail = true;
    }

    @Override
    public void onCUPInvalidPhoneNumber() {
        this.invalidPhoneNumber = true;
    }

    @Override
    public void onCUPFail() {
    }
}