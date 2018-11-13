package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class GetRecordsMockSuccess extends AbstractInteractor implements GetRecords {

    private GetRecords.Callback callback;
    private RecordRepo recordRepo;
    private Problem problem;

    public GetRecordsMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
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
        final ArrayList<Record> records = new ArrayList<>();

        records.add(new Record(
                "Example Problem 1",
                "This record is an example and if you see this in production please create an issue"
        ));

        records.add(new Record(
                "Example Problem 2",
                "This record is an example and if you see this in production please create an issue"
        ));

        records.add(new Record(
                "Example Problem 3",
                "This record is an example and if you see this in production please create an issue"
        ));

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGRSuccess(records);
            }
        });
    }
}
