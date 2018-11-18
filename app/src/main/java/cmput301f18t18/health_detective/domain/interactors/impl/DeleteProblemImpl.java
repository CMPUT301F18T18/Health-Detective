package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class DeleteProblemImpl extends AbstractInteractor implements DeleteProblem {

    private DeleteProblem.Callback callback;
    private ProblemRepo problemRepo;
    private Problem problem;

    public DeleteProblemImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                             DeleteProblem.Callback callback, ProblemRepo problemRepo,
                             Problem problem)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.problem = problem;
    }

    @Override
    public void run() {
        //Problem cannot be found
        if(problemRepo.retrieveProblemById(problem.getProblemID()) == null){
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onDPNotFound();
                }
            });

            return;
        }

        //Delete problem
        this.problemRepo.deleteProblem(problem);
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onDPSuccess(problem);
            }
        });
    }
}
