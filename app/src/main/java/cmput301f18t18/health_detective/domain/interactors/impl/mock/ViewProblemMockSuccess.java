package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.ViewProblem;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class ViewProblemMockSuccess extends AbstractInteractor implements ViewProblem {

    private ViewProblem.Callback callback;
    private RecordRepo recordRepo;
    private Problem problem;

    public ViewProblemMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                  ViewProblem.Callback callback, RecordRepo recordRepo,
                                  Problem problem)
    {
        super();
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
