package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditProblem;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class EditProblemMockSuccess extends AbstractInteractor implements EditProblem {

    private EditProblem.Callback callback;
    private ProblemRepo problemRepo;
    private Problem problemToEdit;
    private String title;
    private String description;
    private Date startDate;

    public EditProblemMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
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

    @Override
    public void run() {
        problemToEdit.setTitle(title);
        problemToEdit.setDescription(description);
        problemToEdit.setStartDate(startDate);

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEPSuccess(problemToEdit);
            }
        });
    }
}
