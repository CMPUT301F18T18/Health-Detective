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
 * The CreateProblemImpl class is a class intended to handle the creation of problems
 * on the back end.
 */
public class CreateProblemImpl extends AbstractInteractor implements CreateProblem {

    private CreateProblem.Callback callback;
    private Patient patient;
    private String problemTitle;
    private String problemDescription;
    private Date startDate;

    /**
     * Constructor for CreateProblemImpl
     * @param callback
     * @param patient the patient the problem is intended to be added to
     * @param problemTitle the title of the created problem
     * @param problemDescription the description of the created problem
     * @param startDate the date chosen for the created problem
     */
    public CreateProblemImpl(CreateProblem.Callback callback,
                             Patient patient, String problemTitle, String problemDescription, Date startDate)
    {
        super();
        this.callback = callback;
        this.patient = patient;
        this.problemTitle = problemTitle;
        this.problemDescription = problemDescription;
        this.startDate = startDate;
    }

    /**
     * Creates a problem and adds it to the database
     *
     * Callbacks:
     *      -Calls onCPNullTitle()
     *          title entered for problem creation is not valid
     *
     *      -Calls onCPSuccess(newProblem)
     *          problem created successfully and added to database as well as a patient
     */
    @Override
    public void run() {
        final UserRepo userRepo = this.context.getUserRepo();
        final ProblemRepo problemRepo = this.context.getProblemRepo();


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
