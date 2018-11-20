package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The CreateProblemImpl class is a class intended to handle to creation of problems
 * on the back end.
 */
public class CreateProblemImpl extends AbstractInteractor implements CreateProblem {

    private CreateProblem.Callback callback;
    private UserRepo userRepo;
    private ProblemRepo problemRepo;
    private Patient patient;
    private String problemTitle;
    private String problemDescription;
    private Date startDate;

    /**
     * Constructor for CreateProblemImpl
     * @param userRepo the repository where users are stored
     * @param problemRepo the repository where problems are stored
     * @param patient the patient the problem is intended to be added to
     * @param problemTitle the title of the created problem
     * @param problemDescription the description of the created problem
     * @param startDate the date chosen for the created problem
     */
    public CreateProblemImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                             CreateProblem.Callback callback, UserRepo userRepo, ProblemRepo problemRepo,
                             Patient patient, String problemTitle, String problemDescription, Date startDate)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.patient = patient;
        this.problemRepo = problemRepo;
        this.problemTitle = problemTitle;
        this.problemDescription = problemDescription;
        this.startDate = startDate;
    }

    /**
     * Main run method for CreateProblemImpl. This method contains all the specific
     * business logic needed for the interactor. The main jobs of this method are to
     * make sure the problem title is not empty, make sure to description is properly
     * set, to set date for the record if one has not been already, and then of course
     * to create the problem.
     */
    @Override
    public void run() {
        if(problemTitle == null) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCPNullTitle();
                }
            });

            return;
        }

        if (problemDescription == null) problemDescription = "";
        Problem newProblem = new Problem(problemTitle,problemDescription);

        if(this.startDate != null){
            newProblem.setStartDate(this.startDate);
        }

        //Add problem to problemRepo and update patient
        problemRepo.insertProblem(newProblem);
        patient.addProblem(newProblem);
        userRepo.updateUser(patient);
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onCPSuccess(newProblem);
            }
        });
    }
}
