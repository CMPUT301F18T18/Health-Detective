package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class GetRecordsImpl extends AbstractInteractor implements GetRecords {

    private GetRecords.Callback callback;
    private RecordRepo recordRepo;
    private Problem problem;

    public GetRecordsImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                          GetRecords.Callback callback, RecordRepo recordRepo,
                          Problem problem)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.recordRepo = recordRepo;
        this.problem = problem;
    }

    @Override
    public void run() {

        if (problem.isRecordsEmpty()) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onGRNoRecords();
                }
            });

            return;
        }

        ArrayList<Integer> recordIds = problem.getRecordIds();
        ArrayList<Record> records = this.recordRepo.retrieveRecordsById(recordIds);

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGRSuccess(records);
            }
        });
    }
}
