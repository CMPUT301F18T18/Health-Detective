package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.interactors.GetProblems;
import cmput301f18t18.health_detective.domain.interactors.impl.GetProblemsImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;

public class ProblemsListPresenter implements GetProblems.Callback, DeleteProblem.Callback {

    private View view;
    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private ProblemRepo problemRepo;
    private UserRepo userRepo;
    private PatientProblemsActivity activity;

    public interface View {
        void onProblemListUpdate(ArrayList<Problem> problemList);
    }

    public ProblemsListPresenter (View view, ThreadExecutor threadExecutor, MainThread mainThread,
                                  ProblemRepo problemRepo, UserRepo userRepo) {
        this.view = view;
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.problemRepo = problemRepo;
        this.userRepo = userRepo;
    }

    public void deleteProblem(Problem problem){

    }

    public void getProblems(Patient patient){
        GetProblems command = new GetProblemsImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.problemRepo,
                patient
        );

        command.execute();
    }

    @Override
    public void onDPSuccess(Problem problem) {

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
