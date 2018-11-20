package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.*;

public class AddAssignedPatientImplTest {
    MainThreadMock mainThread;
    ThreadExecutorMock threadExecutor;
    AddAssignedPatientCallbackMockPresenter callback;
    UserRepoMock users;
    CareProvider careProvider;


    @Before
    public void init() {
        mainThread = new MainThreadMock();
        threadExecutor = new ThreadExecutorMock();
        users = new UserRepoMock();
        callback = new AddAssignedPatientCallbackMockPresenter();
        careProvider = new CareProvider("careprovider");
    }

    @Test
    public void testAddingEmptyString() {
        String testPatientId = "";

        AddAssignedPatientImpl interactor = new AddAssignedPatientImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                careProvider,
                testPatientId
        );

        interactor.execute();

        assertFalse(callback.isAAPSuccess());
        assertFalse(callback.isAAPPatientAlreadyAssigned());
        assertFalse(callback.isAAPPatientDNE());
        assertTrue(callback.isAAPNotValidUserId());
    }

    @Test
    public void testAddingNullId() {
        AddAssignedPatientImpl interactor = new AddAssignedPatientImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                careProvider,
                null
        );

        interactor.execute();

        assertFalse(callback.isAAPSuccess());
        assertFalse(callback.isAAPPatientAlreadyAssigned());
        assertFalse(callback.isAAPPatientDNE());
        assertTrue(callback.isAAPNotValidUserId());
    }

    @Test
    public void testAddingUserThatDoesNotExsistInRepo() {
        String patientIdNotInRepo = "UserNotInRepo";
        assertTrue(User.isValidUserId(patientIdNotInRepo));

        AddAssignedPatientImpl interactor = new AddAssignedPatientImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                careProvider,
                patientIdNotInRepo
        );

        interactor.execute();

        assertFalse(callback.isAAPNotValidUserId());
        assertFalse(callback.isAAPPatientAlreadyAssigned());
        assertFalse(callback.isAAPSuccess());
        assertTrue(callback.isAAPPatientDNE());
    }

    @Test
    public void testAddingUserAlreadyAssignedToCareProvider() {
        String testPatientId = "ThisIdIsValid";
        Patient patient = new Patient(testPatientId);

        assertTrue(User.isValidUserId(testPatientId));

        careProvider.addPatient(patient);
        users.insertUser(patient);
        users.updateUser(careProvider);

        AddAssignedPatientImpl interactor = new AddAssignedPatientImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                careProvider,
                testPatientId
        );

        interactor.execute();

        assertFalse(callback.isAAPNotValidUserId());
        assertFalse(callback.isAAPPatientDNE());
        assertFalse(callback.isAAPSuccess());
        assertTrue(callback.isAAPPatientAlreadyAssigned());
    }

    @Test
    public void testAddingValidId() {
        String testPatientId = "ThisIdIsValid";
        Patient patient = new Patient(testPatientId);

        assertTrue(User.isValidUserId(testPatientId));

        users.insertUser(patient);

        AddAssignedPatientImpl interactor = new AddAssignedPatientImpl(
                threadExecutor,
                mainThread,
                callback,
                users,
                careProvider,
                testPatientId
        );

        interactor.execute();

        assertFalse(callback.isAAPNotValidUserId());
        assertFalse(callback.isAAPPatientAlreadyAssigned());
        assertFalse(callback.isAAPPatientDNE());
        assertTrue(callback.isAAPSuccess());

        CareProvider careProviderFromRepo = users.retrieveCareProviderById(careProvider.getUserId());

        assertTrue(careProviderFromRepo.hasPatient(testPatientId));
    }
}

class AddAssignedPatientCallbackMockPresenter implements AddAssignedPatient.Callback {

    private boolean isAAPSuccess = false;
    private boolean isAAPNotValidUserId = false;
    private boolean isAAPPatientAlreadyAssigned = false;
    private boolean isAAPPatientDNE = false;

    @Override
    public void onAAPSuccess() {
        this.isAAPSuccess = true;
    }

    @Override
    public void onAAPNotValidUserId() {
        this.isAAPNotValidUserId = true;
    }

    @Override
    public void onAAPPatientAlreadyAssigned() {
        this.isAAPPatientAlreadyAssigned = true;
    }

    @Override
    public void onAAPPatientDoesNotExist() {
        this.isAAPPatientDNE = true;
    }

    public boolean isAAPSuccess() {
        return isAAPSuccess;
    }

    public boolean isAAPNotValidUserId() {
        return isAAPNotValidUserId;
    }

    public boolean isAAPPatientAlreadyAssigned() {
        return isAAPPatientAlreadyAssigned;
    }

    public boolean isAAPPatientDNE() {
        return isAAPPatientDNE;
    }
}