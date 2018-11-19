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

        // Check to make sure user created is a patient
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

        // Check to make sure user created is a care provider
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

    @Test
    public void testCUPBlankUserId(){
        String emptyId = "";
        String goodNumber = "(780) 333-7777";
        String goodEmail = "health@ualberta.ca";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                emptyId,
                goodNumber,
                goodEmail,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidUserID());
    }

    @Test
    public void testCUPBlankNumber(){
        String goodId = "GoodBoyId";
        String emptyNumber = "";
        String goodEmail = "healthd@ualberta.ca";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                emptyNumber,
                goodEmail,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidPhoneNumber());
    }

    @Test
    public void testCUPBlankEmail(){
        String goodId = "GoodGuyId";
        String goodNumber = "(780) 333-7777";
        String emptyEmail = "";
        CreateUserProfile command = new CreateUserProfileImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                goodId,
                goodNumber,
                emptyEmail,
                false
        );
        command.execute();

        assertTrue(callback.isCUPInvalidEmail());
    }
}

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