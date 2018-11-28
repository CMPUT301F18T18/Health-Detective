package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;
import java.util.Comparator;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

/**
 * The GetRecordImpl class is a class intended to handle the retrieval of
 * a problem's record list.
 */
public class GetRecordsImpl extends AbstractInteractor implements GetRecords {

    private GetRecords.Callback callback;
    private Problem problem;

    /**
     * Constructor for GetRecordsImpl
     * @param callback
     * @param problem the problem who's list of records is retrieved
     */
    public GetRecordsImpl(GetRecords.Callback callback, Problem problem)
    {
        super();
        this.callback = callback;
        this.problem = problem;
    }

    /**
     * Queries the database for the provided problems records, sorted by date
     *
     * Callbacks:
     *      - onGRNoRecords()
     *          If problem has no records
     *
     *      - onGRSuccess(ArrayList<Records> problems)
     *          If query was successful, passing the problems records as an argument
     */
    @Override
    public void run() {

        final RecordRepo recordRepo = this.context.getRecordRepo();

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
        ArrayList<Record> records = recordRepo.retrieveRecordsById(recordIds);

        records.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                if (o1.getDate().after(o2.getDate())) return -1;
                if (o1.getDate().before(o2.getDate())) return 1;
                return 0;
            }
        });

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGRSuccess(records);
            }
        });
    }
}
