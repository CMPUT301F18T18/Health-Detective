package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;

import static org.junit.Assert.*;

public class CreateProblemImplTest {




}

// Mock presenter to use for tests
/*class CreateProblemMockPresenter implements CreateProblem.Callback {

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
    public void onCPSuccess() {

    }

    @Override
    public void onCPNullTitle() {

    }

    @Override
    public void onCPFail() {
    }
}*/