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

public class CreateUserProfileTest {

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

    public Patient getLoggedInPatient() {
        return createdPatient;
    }

    public CareProvider getLoggedInCareProvider() {
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

}