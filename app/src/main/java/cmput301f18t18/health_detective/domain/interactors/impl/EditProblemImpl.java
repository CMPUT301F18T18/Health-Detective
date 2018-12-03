package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditProblem;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

/**
 * The EditProblemImpl class is a class intended to handle the editing of problems
 * on the back end.
 */
public class EditProblemImpl extends AbstractInteractor implements EditProblem {

    private EditProblem.Callback callback;
    private String title;
    private String description;
    private Date startDate;

    /**
     * First constructor for EditProblemImpl
     * @param callback
     * @param title the title of the problem that is being edited
     * @param description the description of the problem being edited
     * @param startDate the date assigned to the problem being edited
     */
    public EditProblemImpl(EditProblem.Callback callback,
                           String title, String description, Date startDate)
    {
        super();
        this.callback = callback;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
    }

    /**
     * Edits the information contained in a problem and updates the database.
     *
     * Callbacks:
     *      -Calls onEPEmptyTitle()
     *          if title is empty (enforcing title)
     *
     *      -Calls onEPNoStartDateProvided()
     *          assigns date if one is not provided
     *
     *      -Calls onEPSuccess(problemToEdit)
     *          if editing problem is successful, updates everything containing problem
     */
    @Override
    public void run() {
        final ProblemRepo problemRepo = this.context.getProblemRepo();
        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        User loggedInUser = treeParser.getLoggedInUser();
        Problem problemToEdit = treeParser.getCurrentProblemContext();

        if (loggedInUser instanceof CareProvider) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onEPInvalidPermissions();
                }
            });
        }

        // Missing title
        if (this.title == null || this.title.isEmpty()) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onEPEmptyTitle();
                }
            });

            return;
        }

        // Missing Date
        if (this.startDate == null) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onEPNoStartDateProvided();
                }
            });

            return;
        }

        if (this.description == null) {
            this.description = "";
        }

        problemToEdit.setTitle(this.title);
        problemToEdit.setDescription(this.description);
        problemToEdit.setStartDate(this.startDate);

        problemRepo.updateProblem(problemToEdit);

        // Problem added
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onEPSuccess(problemToEdit);
            }
        });
    }
}
