package cmput301f18t18.health_detective.domain.interactors.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.interactors.EditProblem;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

import static org.junit.Assert.*;

public class EditProblemImplTest {
    private MainThread mainThread;
    private ThreadExecutor threadExecutor;
    private EditProblemMockPresenter callback;
    private UserRepo users;
    private ProblemRepo problems;

    @Before
    public void init() {
        this.mainThread = new MainThreadMock();
        this.threadExecutor = new ThreadExecutorMock();
        this.problems = new ProblemRepoMock();
        this.callback = new EditProblemMockPresenter();
    }

    // Testing problem is correctly created
    @Test
    public void testEPProblem() {
        Problem problemToEdit = new Problem();
        problems.insertProblem(problemToEdit);

        String title = "Title";
        String description = "description";
        Date date = new Date();

        EditProblem command = new EditProblemImpl(
                callback,
                title,
                description,
                date
        );
        command.execute();

        // Check to make sure problem is created
        assertTrue(callback.isEPSuccessProblem());

        Problem problem = callback.getEditedProblem();

        // Check to make sure all the attributes of the problem were correctly assigned
        assertNotNull(problem);
        assertEquals("Title", problem.getTitle());
        assertEquals("description", problem.getDescription());

        // Check problem repo has been updated
        assertEquals(problems.retrieveProblemById(problem.getProblemId()).getProblemId(), problem.getProblemId());
    }

    // Testing problem is correctly created
    @Test
    public void testEPProblemNullDescription() {
        Problem problemToEdit = new Problem();
        problems.insertProblem(problemToEdit);

        String title = "Title";
        String description = null;
        Date date = new Date();

        EditProblem command = new EditProblemImpl(
                callback,
                title,
                description,
                date
        );
        command.execute();

        // Check to make sure problem is created
        assertTrue(callback.isEPSuccessProblem());

        Problem problem = callback.getEditedProblem();

        // Check to make sure all the attributes of the problem were correctly assigned
        assertNotNull(problem);
        assertEquals("Title", problem.getTitle());
        assertEquals("", problem.getDescription());

        // Check problem repo has been updated
        assertEquals(problems.retrieveProblemById(problem.getProblemId()).getProblemId(), problem.getProblemId());
    }

    // Testing null title
    @Test
    public void testCPNullTitle(){
        Problem problemToEdit = new Problem();
        problems.insertProblem(problemToEdit);

        String title = null;
        String description = "description";
        Date date = new Date();
        EditProblem command = new EditProblemImpl(
                callback,
                title,
                description,
                date
        );
        command.execute();


        assertTrue(callback.isEPNullTitle());
    }

    // Testing null date
    @Test
    public void testEPNoDate(){
        Problem problemToEdit = new Problem();
        problems.insertProblem(problemToEdit);

        String title = "This is title";
        String description = "description";
        Date date = null;
        EditProblem command = new EditProblemImpl(
                callback,
                title,
                description,
                date
        );
        command.execute();


        assertTrue(callback.isNoDate());
    }


}

// Mock presenter to use for tests
class EditProblemMockPresenter implements EditProblem.Callback {

    private boolean EPSuccessProblem = false;
    private boolean nullTitle = false;
    private boolean noDate = false;
    private Problem editedProblem = null;

    public boolean isEPSuccessProblem() {
        return EPSuccessProblem;
    }

    public boolean isEPNullTitle() {
        return nullTitle;
    }

    public boolean isNoDate() {
        return noDate;
    }

    public Problem getEditedProblem() {
        return editedProblem;
    }

    @Override
    public void onEPSuccess(Problem problem) {
        this.EPSuccessProblem = true;
        this.editedProblem = problem;
    }

    @Override
    public void onEPEmptyTitle() {
        this.nullTitle = true;
    }

    @Override
    public void onEPNoStartDateProvided() {
        this.noDate = true;
    }

    @Override
    public void onEPInvalidPermissions() {

    }
}