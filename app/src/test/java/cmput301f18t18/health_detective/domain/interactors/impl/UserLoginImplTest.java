package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.*;

public class UserLoginImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private UserLoginMockPresenter callback;
    private UserRepo users;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.users = new UserRepoMock();
        this.callback = new UserLoginMockPresenter();

        Patient patient1 = new Patient(
                "Patient1",
                "780-318-0000",
                "patient1@email.com"

        );

        Patient patient2 = new Patient(
                "Patient2",
                "780-318-1111",
                "patient2@email.com"

        );

        CareProvider careProvider = new CareProvider(
                "CareProvider",
                "780-318-2222",
                "careprovider@email.com"

        );

        users.insertUser(patient1);
        users.insertUser(patient2);
        users.insertUser(careProvider);
    }

    @Test
    public void testLoginPatient(){
        UserLogin command = new UserLoginImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                "Patient1"
        );
        command.execute();

        assertTrue(callback.isLoginSuccessPatient());

        Patient patient = callback.getLoggedInPatient();

        assertNotNull(patient);
        assertEquals("Patient1", patient.getUserId());
        assertEquals("780-318-0000", patient.getPhoneNumber());
        assertEquals("patient1@email.com", patient.getEmailAddress());
    }

    @Test
    public void testLoginCareProvider(){
        UserLogin command = new UserLoginImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                "CareProvider"
        );
        command.execute();

        assertTrue(callback.isLoginSuccessCareProvider());

        CareProvider careProvider = callback.getLoggedInCareProvider();

        assertNotNull(careProvider);
        assertEquals("CareProvider", careProvider.getUserId());
        assertEquals("780-318-2222", careProvider.getPhoneNumber());
        assertEquals("careprovider@email.com", careProvider.getEmailAddress());
    }

    @Test
    public void testLoginNullUserId(){
        UserLogin command = new UserLoginImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                null
        );
        command.execute();

        assertTrue(callback.isInvalidUserId());
    }

    @Test
    public void testLoginBlankUserId(){
        UserLogin command = new UserLoginImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                ""
        );
        command.execute();

        assertTrue(callback.isInvalidUserId());
    }

    @Test
    public void testLoginSevenDigitUserId(){
        UserLogin command = new UserLoginImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                "seven12"
        );
        command.execute();

        assertTrue(callback.isInvalidUserId());
    }

    @Test
    public void testLoginUserIdWithWhitespace(){
        UserLogin command = new UserLoginImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                "seven 12"
        );
        command.execute();

        assertTrue(callback.isInvalidUserId());
    }

    @Test
    public void testLoginUserIdNoneExsistant(){
        UserLogin command = new UserLoginImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                "IdontExistUser"
        );
        command.execute();

        assertTrue(callback.isLoginUserDoesNotExsist());
    }


}

class UserLoginMockPresenter implements UserLogin.Callback {

    private boolean loginSuccessPatient = false;
    private boolean loginSuccessCareProvider = false;
    private boolean loginUserDoesNotExsist = false;
    private boolean failedToDetermineType = false;
    private boolean invalidUserId = false;
    private Patient loggedInPatient = null;
    private CareProvider loggedInCareProvider = null;

    public boolean isLoginSuccessPatient() {
        return loginSuccessPatient;
    }

    public boolean isLoginSuccessCareProvider() {
        return loginSuccessCareProvider;
    }

    public boolean isLoginUserDoesNotExsist() {
        return loginUserDoesNotExsist;
    }

    public boolean isFailedToDetermineType() {
        return failedToDetermineType;
    }

    public boolean isInvalidUserId() {
        return invalidUserId;
    }

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public CareProvider getLoggedInCareProvider() {
        return loggedInCareProvider;
    }

    @Override
    public void onLoginPatientSuccess(Patient patient) {
        this.loginSuccessPatient = true;
        this.loggedInPatient = patient;
    }

    @Override
    public void onLoginCareProviderSuccess(CareProvider careProvider) {
        this.loginSuccessCareProvider = true;
        this.loggedInCareProvider = careProvider;
    }

    @Override
    public void onLoginInvalidUserId() {
        this.invalidUserId = true;
    }

    @Override
    public void onLoginUserDoesNotExist() {
        this.loginUserDoesNotExsist = true;
    }

    @Override
    public void onLoginCouldNotDetemineUserType() {
        this.failedToDetermineType = true;
    }
}