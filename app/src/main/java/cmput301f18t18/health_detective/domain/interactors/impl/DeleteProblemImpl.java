package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The DeleteProblemImpl class is a class intended to handle the deletion of problems
 * on the back end.
 */
public class DeleteProblemImpl extends AbstractInteractor implements DeleteProblem {

    private DeleteProblem.Callback callback;
    private UserRepo userRepo;
    private ProblemRepo problemRepo;
    private Patient patient;
    private Problem problem;

    /**
     * Constructor for DeleteProblemImpl
     * @param threadExecutor
     * @param mainThread
     * @param callback
     * @param userRepo the repository where users are stored
     * @param problemRepo the repository where problems are stored
     * @param patient the patient the problem being deleted belonged to
     * @param problem the problem being deleted
     */
    public DeleteProblemImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                             DeleteProblem.Callback callback, UserRepo userRepo, ProblemRepo problemRepo,
                             Patient patient, Problem problem)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.problemRepo = problemRepo;
        this.patient = patient;
        this.problem = problem;
    }

    /**
     * Deletes a problem from a patient and the database
     *
     * Callbacks:
     *      -Calls onDPPatientNotFound()
     *          patient owning the problem does not exist
     *
     *      -Calls onDPProblemNotFound()
     *          problem attempting to be deleted does not exist
     *
     *      -Calls onDPSuccess(problem)
     *          if deleting problem is successful, updates everything containing problem
     */
    @Override
    public void run() {
        // Patient cannot be found
        if(userRepo.retrievePatientById(patient.getUserId()) == null){
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onDPPatientNotFound();
                }
            });

            return;
        }
        // Problem cannot be found
        if(problemRepo.retrieveProblemById(problem.getProblemID()) == null){
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onDPProblemNotFound();
                }
            });

            return;
        }

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onDPSuccess(problem);
            }
        });

        //Delete problem
        this.problemRepo.deleteProblem(problem);
        this.patient.removeProblem(problem);
        this.userRepo.updateUser(patient);
    }
}
