package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.interactors.GetProblems;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateProblemImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.DeleteProblemImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetProblemsImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;

public class ProblemsListPresenter implements GetProblems.Callback, DeleteProblem.Callback{

    private View view;


    public interface View {
        void onProblemListUpdate(ArrayList<Problem> problemList);
        void onProblemDeleted(Problem problem);
    }

    public ProblemsListPresenter (View view) {
        this.view = view;
    }

    /**
     * Method that calls on interactor that will delete problem
     * @param patientContext current user that is deleting the problem
     * @param problem current problem getting deleted
     */
    public void deleteProblem(Patient patientContext, Problem problem){
        DeleteProblem command = new DeleteProblemImpl(
                this,
                patientContext,
                problem
        );

        command.execute();
    }

    /**
     * Method that calls on the interactor that gets all the problems for the current user
     * @param patient current user (patitent)
     */
    public void getProblems(Patient patient){
        GetProblems command = new GetProblemsImpl(
                this,
                patient);

        command.execute();
    }


    @Override
    public void onDPSuccess(Problem problem) {
        this.view.onProblemDeleted(problem);
    }

    @Override
    public void onDPPatientNotFound() {

    }

    @Override
    public void onDPProblemNotFound() {

    }

    @Override
    public void onDPFail() {

    }

    @Override
    public void onGPSuccess(ArrayList<Problem> patientProblems) {
        this.view.onProblemListUpdate(patientProblems);
    }

    @Override
    public void onGPNoProblems() {
        this.view.onProblemListUpdate(new ArrayList<Problem>());
    }

}
