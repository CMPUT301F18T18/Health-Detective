package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DeleteProblemImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private DeleteProblemMockPresenter callback;
    private UserRepo users;
    private ProblemRepo problems;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.users = new UserRepoMock();
        this.problems = new ProblemRepoMock();
        this.callback = new DeleteProblemMockPresenter();

        Patient patient1 = new Patient(
                "Patient1",
                "(780) 318-0000",
                "patient1@email.com"

        );
        users.insertUser(patient1);
        Date date = new Date();
        Problem problem1 = new Problem(
                1234,
                "title1",
                "description1",
                date
        );
        Problem problem2 = new Problem(
                4321,
                "title2",
                "description2",
                date
        );
        problems.insertProblem(problem1);
        problems.insertProblem(problem2);
        patient1.addProblem(problem1);
        patient1.addProblem(problem2);
    }

    // Testing if the problem is correctly deleted
    @Test
    public void testDPProblem() {

        DeleteProblem command = new DeleteProblemImpl(
                callback,
                users.retrievePatientById("Patient1"),
                problems.retrieveProblemById(1234)
        );
        command.execute();

        // Check to make sure problem is deleted
        assertTrue(callback.isDPSuccessProblem());

        Problem problem = callback.getDeletedProblem();

        // Check to make sure problem deleted is not null
        assertNotNull(problem);

        // Check to make sure problem has been removed from repo
        assertEquals(problems.retrieveProblemById(problem.getProblemID()), null);

        // Check problem has been removed from patient
        assertFalse(users.retrievePatientById("Patient1").getProblemIds().contains(1234));

        // Check to make sure non-deleted problem is still in repo
        assertFalse(problems.retrieveProblemById(4321).equals(null));

        // Check to make sure non-deleted problem is still in patient's list
        assertTrue(users.retrievePatientById("Patient1").getProblemIds().contains(4321));

    }

    // Testing to make sure cannot find patient check is working
    @Test
    public void testDPPatientNotFound() {

        Patient patient = new Patient("HelloThere");
        DeleteProblem command = new DeleteProblemImpl(
                callback,
                patient,
                problems.retrieveProblemById(1234)
        );
        command.execute();

        assertTrue(callback.isDPUnfoundPatient());
    }

    // Testing to make sure cannot find problem check is working
    @Test
    public void testDPProblemNotFound() {

        Date date = new Date();
        Problem problem = new Problem(
                1111,
                "Title",
                "Description",
                 date
        );
        DeleteProblem command = new DeleteProblemImpl(
                callback,
                users.retrievePatientById("Patient1"),
                problem
        );
        command.execute();

        assertTrue(callback.isDPUnfoundProblem());
    }

}


// Mock presenter to use for tests
class DeleteProblemMockPresenter implements DeleteProblem.Callback {

    private boolean DPSuccessProblem = false;
    private boolean nullPatient = false;
    private boolean nullProblem = false;
    private Problem deletedProblem = null;

    public boolean isDPSuccessProblem() {
        return DPSuccessProblem;
    }

    public boolean isDPUnfoundPatient() {
        return nullPatient;
    }

    public boolean isDPUnfoundProblem() {
        return nullProblem;
    }

    public Problem getDeletedProblem() {
        return deletedProblem;
    }

    @Override
    public void onDPSuccess(Problem problem) {
        this.DPSuccessProblem = true;
        this.deletedProblem = problem;
    }

    @Override
    public void onDPPatientNotFound() {
        this.nullPatient = true;
    }

    @Override
    public void onDPProblemNotFound() {
        this.nullProblem = true;
    }

    @Override
    public void onDPFail() {
    }
}