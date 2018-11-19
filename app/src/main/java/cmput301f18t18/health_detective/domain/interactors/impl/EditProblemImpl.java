package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditProblem;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class EditProblemImpl extends AbstractInteractor implements EditProblem {

    private EditProblem.Callback callback;
    private ProblemRepo problemRepo;
    private Problem problemToEdit;
    private String title;
    private String description;
    private Date startDate;

    public EditProblemImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                           EditProblem.Callback callback, ProblemRepo problemRepo,
                           Problem problemToEdit, String title, String description, Date startDate)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.problemToEdit = problemToEdit;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
    }

    public EditProblemImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                           EditProblem.Callback callback, ProblemRepo problemRepo,
                           Problem problemToEdit, String title, String description)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.problemToEdit = problemToEdit;
        this.description = description;
        this.title = title;
        this.startDate = problemToEdit.getStartDate();
    }

    public EditProblemImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                           EditProblem.Callback callback, ProblemRepo problemRepo,
                           Problem problemToEdit, String description)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.problemToEdit = problemToEdit;
        this.description = description;
        this.title = problemToEdit.getTitle();
        this.startDate = problemToEdit.getStartDate();

    }

    @Override
    public void run() {
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

        this.problemToEdit.setTitle(this.title);
        this.problemToEdit.setDescription(this.description);
        this.problemToEdit.setStartDate(this.startDate);

        this.problemRepo.updateProblem(this.problemToEdit);

        // Problem added
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onEPSuccess(problemToEdit);
            }
        });
    }
}
