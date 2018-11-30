package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class CreateRecordMockSuccess extends AbstractInteractor implements CreateRecord {

    private CreateRecord.Callback callback;
    private ProblemRepo problemRepo;
    private RecordRepo recordRepo;
    private Problem problem;
    private String recordTitle;
    private String recordComment;
    private Date date;

    public CreateRecordMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                   CreateRecord.Callback callback, ProblemRepo problemRepo, RecordRepo recordRepo,
                                   Problem problem, String recordTitle, String recordComment, Date date)
    {
        super();
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.recordRepo = recordRepo;
        this.problem = problem;
        this.recordTitle = recordTitle;
        this.recordComment = recordComment;
        this.date = date;
    }

    @Override
    public void run() {

    }
}
