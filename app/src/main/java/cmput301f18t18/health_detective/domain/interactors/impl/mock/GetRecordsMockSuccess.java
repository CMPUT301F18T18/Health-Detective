package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class GetRecordsMockSuccess extends AbstractInteractor implements GetRecords {

    private GetRecords.Callback callback;
    private ProblemRepo problemRepo;
    private Problem problem;

    public GetRecordsMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                 GetRecords.Callback callback, ProblemRepo problemRepo,
                                 Problem problem)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.problem = problem;
    }

    @Override
    public void run() {

    }
}
