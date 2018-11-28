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
    private Patient patient;
    private Problem problem;

    /**
     * Constructor for DeleteProblemImpl
     * @param callback
     * @param patient the patient the problem being deleted belonged to
     * @param problem the problem being deleted
     */
    public DeleteProblemImpl(DeleteProblem.Callback callback, Patient patient, Problem problem)
    {
        super();
        this.callback = callback;
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
        final UserRepo userRepo = this.context.getUserRepo();
        final ProblemRepo problemRepo = this.context.getProblemRepo();

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
        problemRepo.deleteProblem(problem);
        this.patient.removeProblem(problem);
        userRepo.updateUser(patient);
    }
}
