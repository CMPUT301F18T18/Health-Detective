//package cmput301f18t18.health_detective.domain.interactors.impl;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//import cmput301f18t18.health_detective.domain.executor.MainThread;
//import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
//import cmput301f18t18.health_detective.domain.executor.mock.MainThreadMock;
//import cmput301f18t18.health_detective.domain.executor.mock.ThreadExecutorMock;
//import cmput301f18t18.health_detective.domain.interactors.ViewPatient;
//import cmput301f18t18.health_detective.domain.model.Patient;
//import cmput301f18t18.health_detective.domain.model.Problem;
//import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
//import cmput301f18t18.health_detective.domain.repository.UserRepo;
//import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
//import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;
//
//import static org.junit.Assert.*;
//
//public class ViewPatientImplTest {
//    private MainThread mainThread;
//    private ThreadExecutor threadExecutor;
//    private GetProblemsMockPresenter callback;
//    private UserRepo users;
//    private ProblemRepo problems;
//
//    @Before
//    public void setUp() {
//        this.mainThread = new MainThreadMock();
//        this.threadExecutor = new ThreadExecutorMock();
//        this.users = new UserRepoMock();
//        this.problems = new ProblemRepoMock();
//        this.callback = new GetProblemsMockPresenter();
//    }
//
//    @Test
//    public void patientHasRecords() {
//        Date dateToUse1 = new Date();
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(2);
//        } catch (InterruptedException e) {
//        }
//
//        Date dateToUse2 = new Date();
//
//        Patient testPatient = new Patient("patient");
//        Problem testProblem1 = new Problem(1,"Title1", "Comment1", (Date) dateToUse1.clone());
//        Problem testProblem2 = new Problem(2,"Title2","Comment2", (Date) dateToUse2.clone());
//
//        testPatient.addProblem(testProblem1);
//        testPatient.addProblem(testProblem2);
//        problems.insertProblem(testProblem1);
//        problems.insertProblem(testProblem2);
//        users.insertUser(testPatient);
//
//        ViewPatient interactor = new ViewPatientImpl(
//                callback,
//                testPatient
//        );
//
//        interactor.execute();
//
//        ArrayList<Problem> compareProblems = new ArrayList<>();
//        Problem compareProblem1 = new Problem(1,"Title1", "Comment1", (Date) dateToUse1.clone());
//        Problem compareProblem2 = new Problem(2,"Title2","Comment2", (Date) dateToUse2.clone());
//
//        compareProblems.add(compareProblem2);
//        compareProblems.add(compareProblem1);
//
//        assertTrue(callback.isSuccess());
//        assertFalse(callback.isNoProblems());
//
//        assertEquals(compareProblems, callback.getRetrievedProblems());
//    }
//
//    @Test
//    public void patientHasNoRecords() {
//
//        Patient testPatient = new Patient();
//        users.insertUser(testPatient);
//
//        ViewPatient interactor = new ViewPatientImpl(
//                callback,
//                testPatient
//        );
//
//        interactor.execute();
//
//        assertTrue(callback.isNoProblems());
//        assertFalse(callback.isSuccess());
//    }
//
//}
//
//class GetProblemsMockPresenter implements ViewPatient.Callback {
//
//    private boolean isSuccess = false;
//    private  boolean isNoProblems = false;
//    private ArrayList<Problem> retrievedProblems = null;
//
//    public boolean isSuccess() {
//        return isSuccess;
//    }
//
//    public boolean isNoProblems() {
//        return isNoProblems;
//    }
//
//    public ArrayList<Problem> getRetrievedProblems() {
//        return retrievedProblems;
//    }
//
//    @Override
//    public void onGPSuccess(ArrayList<Problem> problems) {
//        this.isSuccess = true;
//        this.retrievedProblems = problems;
//    }
//
//    @Override
//    public void onGPNoProblems() {
//        this.isNoProblems = true;
//    }
//}