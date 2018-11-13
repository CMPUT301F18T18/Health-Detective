package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class GetRecordsImpl extends AbstractInteractor implements GetRecords {

    private GetRecords.Callback callback;
    private ProblemRepo problemRepo;
    private Problem problem;

    public GetRecordsImpl(ThreadExecutor threadExecutor, MainThread mainThread,
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
        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGRFail();
            }
        });
    }
}
