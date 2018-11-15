package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetProblems;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class GetProblemsImpl extends AbstractInteractor implements GetProblems {

    private GetProblems.Callback callback;
    private ProblemRepo problemRepo;
    private Patient patient;

    public GetProblemsImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                           GetProblems.Callback callback, ProblemRepo problemRepo,
                           Patient patient)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.patient = patient;
    }

    @Override
    public void run() {

        if (patient.isProblemsEmpty()) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onGPNoProblems();
                }
            });

            return;
        }

        ArrayList<Integer> problemIds = patient.getProblemIds();
        ArrayList<Problem> problems = this.problemRepo.retrieveProblemsById(problemIds);

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGPSuccess(problems);
            }
        });
    }
}
