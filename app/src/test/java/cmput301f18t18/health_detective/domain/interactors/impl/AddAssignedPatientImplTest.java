package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.model.CareProvider;
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
        careProvider = new CareProvider();
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
    }
}

class AddAssignedPatientCallbackMockPresenter implements AddAssignedPatient.Callback {

    private CareProvider careProvider;
    private boolean isAAPSuccess = false;

    @Override
    public void onAAPSuccess(CareProvider careProvider) {
        this.isAAPSuccess = true;
        this.careProvider = careProvider;
    }

    @Override
    public void onAAPFail() {
        this.isAAPSuccess = false;
    }

    public CareProvider getCareProvider() {
        return careProvider;
    }

    public boolean isAAPSuccess() {
        return isAAPSuccess;
    }
}