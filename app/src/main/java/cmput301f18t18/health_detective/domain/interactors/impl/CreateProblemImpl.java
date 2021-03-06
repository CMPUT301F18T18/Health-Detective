package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

/**
 * The CreateProblemImpl class is a class intended to handle the creation of problems
 * on the back end.
 */
public class CreateProblemImpl extends AbstractInteractor implements CreateProblem {

    private CreateProblem.Callback callback;
    private String problemTitle;
    private String problemDescription;
    private Date startDate;

    /**
     * Constructor for CreateProblemImpl
     * @param callback
     * @param problemTitle the title of the created problem
     * @param problemDescription the description of the created problem
     * @param startDate the date chosen for the created problem
     */
    public CreateProblemImpl(CreateProblem.Callback callback,
                             String problemTitle, String problemDescription, Date startDate)
    {
        super();
        this.callback = callback;
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
        final Patient patient;

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

        // Get patient to add problem to
        ContextTreeParser contextTreeParser = new ContextTreeParser(context.getContextTree());
        patient = contextTreeParser.getCurrentPatientContext();

        // If null patient is not in scope, call is invalid
        if (patient == null) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onCPNoPatientInScope();
                }
            });

            return;
        }

        //Add problem to problemRepo and update patient
        problemRepo.insertProblem(newProblem);
        patient.addProblem(newProblem);
        userRepo.updateUser(patient);

        new PutContext(patient).execute();

        // Return problem so UI can proceed
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onCPSuccess(newProblem);
            }
        });

    }
}
