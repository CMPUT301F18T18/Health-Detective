package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.RemoveAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.UserLogin;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.*;

public class RemoveAssignedPatientImplTest {

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private UserRepo userRepo;
    private RemoveAssignedPatientMockPresenter callback;

    @Before
    public void testInit(){
        this.threadExecutor = new ThreadExecutorMock();
        this.mainThread = new MainThreadMock();
        this.callback = new RemoveAssignedPatientMockPresenter();
        this.userRepo = new UserRepoMock();

        // Init UserRepo to have some values;
        CareProvider careProvider1 = new CareProvider(
                "testCareProvider1",
                "780-111-1111",
                "testCareProvider1@email.com"
        );

        CareProvider careProvider2 = new CareProvider(
                "testCareProvider2",
                "780-333-3333",
                "testcareprovider2@email.com"
        );

        Patient patient1 = new Patient(
                "testPatient1",
                "780-222-2222",
                "testpatient1@email.com"
        );

        Patient patient2 = new Patient(
                "testPatient2",
                "780-444-4444",
                "testpatient2@email.com"
        );

        Patient patient3 = new Patient(
                "testPatient3",
                "780-555-5555",
                "testpatient3@email.com"
        );

        Patient patient4 = new Patient(
                "testPatient4",
                "780-666-6666",
                "testpatient4@email.com"
        );

        careProvider1.addPatient(patient2);
        careProvider1.addPatient(patient1);
        careProvider2.addPatient(patient1);
        careProvider2.addPatient(patient4);
        careProvider2.addPatient(patient3);

        this.userRepo.insertUser(careProvider1);
        this.userRepo.insertUser(careProvider2);
        this.userRepo.insertUser(patient1);
        this.userRepo.insertUser(patient2);
        this.userRepo.insertUser(patient3);
        this.userRepo.insertUser(patient4);
    }

    @Test
    public void testUserNotAssignedToCareprovider() {

        CareProvider careProviderToTest = userRepo.retrieveCareProviderById("testCareProvider1");
        Patient patientToRemove = userRepo.retrievePatientById("testPatient4");

        RemoveAssignedPatient command = new RemoveAssignedPatientImpl(
                threadExecutor,
                mainThread,
                callback,
                userRepo,
                careProviderToTest,
                patientToRemove
        );

        command.execute();

        assertTrue(callback.isRapPatientNotAssigned());
    }

    @Test
    public void testUserAssignedToCareprovider() {

        CareProvider careProviderToTest = userRepo.retrieveCareProviderById("testCareProvider1");
        Patient patientToRemove = userRepo.retrievePatientById("testPatient2");

        RemoveAssignedPatient command = new RemoveAssignedPatientImpl(
                threadExecutor,
                mainThread,
                callback,
                userRepo,
                careProviderToTest,
                patientToRemove
        );

        command.execute();

        assertTrue(callback.isRapSuccess());

        Patient patient2 = new Patient(
                "testPatient2",
                "780-444-4444",
                "testpatient2@email.com"
        );

        assertEquals(callback.getRemovedPatient(), patient2);
    }
}

class RemoveAssignedPatientMockPresenter implements RemoveAssignedPatient.Callback {

    private boolean rapPatientNotAssigned = false;
    private boolean rapSuccess = false;
    private Patient removedPatient;

    public boolean isRapPatientNotAssigned() {
        return rapPatientNotAssigned;
    }

    public boolean isRapSuccess() {
        return rapSuccess;
    }

    public Patient getRemovedPatient() {
        return removedPatient;
    }

    @Override
    public void onRAPSuccess(Patient removedPatient) {
        this.rapSuccess = true;
        this.removedPatient = removedPatient;

    }

    @Override
    public void onRAPPatientNotAssigned() {
        this.rapPatientNotAssigned = true;
    }
}