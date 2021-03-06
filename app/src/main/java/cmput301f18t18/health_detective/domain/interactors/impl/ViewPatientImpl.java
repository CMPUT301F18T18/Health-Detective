package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;
import java.util.Comparator;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.ViewPatient;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

/**
 * The GetProblemImpl class is a class intended to handle the retrieval of
 * a patient's problems.
 */
public class ViewPatientImpl extends AbstractInteractor implements ViewPatient {

    private Callback callback;

    /**
     * Constructor for GetProblemImpl
     * @param callback
     */
    public ViewPatientImpl(Callback callback)
    {
        super();
        this.callback = callback;
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
        if (callback == null)
            return;

        ProblemRepo problemRepo = this.context.getProblemRepo();
        ContextTree tree = this.context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        final Patient patient = treeParser.getCurrentPatientContext();

        if (patient == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVPaNoContext();
                }
            });

            return;
        }

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onVPaSuccessDetails(patient.getUserId(), patient.getEmailAddress(), patient.getPhoneNumber());
            }
        });

        if (patient.isProblemsEmpty()) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onVPaNoProblems();
                }
            });

            return;
        }

        ArrayList<String> problemIds = patient.getProblemIds();
        ArrayList<Problem> problems = problemRepo.retrieveProblemsById(problemIds);

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
                callback.onVPaSuccess(problems);
            }
        });
    }
}
