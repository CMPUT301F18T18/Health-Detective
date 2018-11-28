package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;
import java.util.Comparator;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetProblems;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

/**
 * The GetProblemImpl class is a class intended to handle the retrieval of
 * a patient's problems.
 */
public class GetProblemsImpl extends AbstractInteractor implements GetProblems {

    private GetProblems.Callback callback;
    private ProblemRepo problemRepo;
    private Patient patient;

    /**
     * Constructor for GetProblemImpl
     * @param threadExecutor
     * @param mainThread
     * @param callback
     * @param problemRepo the repository where problems are stored
     * @param patient the patient who's list of problems is retrieved
     */
    public GetProblemsImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                           GetProblems.Callback callback, ProblemRepo problemRepo,
                           Patient patient)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.patient = patient;
    }

    /**
     * Queries the database for the provided patient's problems, sorted by date
     *
     * Callbacks:
     *      - onGPNoProblems()
     *          If patient has no records
     *
     *      - onGPSuccess(ArrayList<Problems> problems)
     *          If query was successful, passing the problems records as an argument
     */
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

        problems.sort(new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                if (o1.getStartDate().after(o2.getStartDate())) return -1;
                if (o1.getStartDate().before(o2.getStartDate())) return 1;
                return 0;
            }
        });

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGPSuccess(problems);
            }
        });
    }
}
