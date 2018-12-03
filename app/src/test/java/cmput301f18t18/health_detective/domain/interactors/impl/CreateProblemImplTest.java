package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.*;

public class CreateProblemImplTest {

    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private CreateProblemMockPresenter callback;
    private UserRepo users;
    private ProblemRepo problems;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.users = new UserRepoMock();
        this.problems = new ProblemRepoMock();
        this.callback = new CreateProblemMockPresenter();

        Patient patient1 = new Patient(
                "Patient1",
                "(780) 318-0000",
                "patient1@email.com"

        );
        users.insertUser(patient1);
    }

    // Testing problem is correctly created
    @Test
    public void testCPProblem() {
        String title = "Title";
        String description = "description";
        Date date = new Date();

        CreateProblem command = new CreateProblemImpl(
                callback,
                title,
                description,
                date
        );
        command.execute();

        // Check to make sure problem is created
        assertTrue(callback.isCPSuccessProblem());

        Problem problem = callback.getCreatedProblem();

        // Check to make sure all the attributes of the problem were correctly assigned
        assertNotNull(problem);
        assertEquals("Title", problem.getTitle());
        assertEquals("description", problem.getDescription());

        // Check problem repo has been updated
        assertEquals(problems.retrieveProblemById(problem.getProblemId()).getProblemId(), problem.getProblemId());

        // Check problem has been added to user
        assertTrue(users.retrievePatientById("Patient1").getProblemIds().contains(problem.getProblemId()));
    }

    // Testing null title
    @Test
    public void testCPNullTitle(){
        String title = null;
        String description = "description";
        Date date = new Date();
        CreateProblem command = new CreateProblemImpl(
                callback,
                title,
                description,
                date
        );
        command.execute();

        assertTrue(callback.isCPNullTitle());
    }


}

// Mock presenter to use for tests
class CreateProblemMockPresenter implements CreateProblem.Callback {

    private boolean CPSuccessProblem = false;
    private boolean nullTitle = false;
    private Problem createdProblem = null;

    public boolean isCPSuccessProblem() {
        return CPSuccessProblem;
    }

    public boolean isCPNullTitle() {
        return nullTitle;
    }

    public Problem getCreatedProblem() {
        return createdProblem;
    }

    @Override
    public void onCPSuccess(Problem problem) {
        this.CPSuccessProblem = true;
        this.createdProblem = problem;
    }

    @Override
    public void onCPNullTitle() {
        this.nullTitle = true;
    }

    @Override
    public void onCPFail() {
    }

    @Override
    public void onCPNoPatientInScope() {

    }
}