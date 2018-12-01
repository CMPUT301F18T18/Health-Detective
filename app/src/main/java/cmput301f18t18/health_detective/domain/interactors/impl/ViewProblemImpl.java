package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;
import java.util.Comparator;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.ViewProblem;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

/**
 * The GetRecordImpl class is a class intended to handle the retrieval of
 * a problem's record list.
 */
public class ViewProblemImpl extends AbstractInteractor implements ViewProblem {

    private ViewProblem.Callback callback;

    /**
     * Constructor for GetRecordsImpl
     * @param callback
     */
    public ViewProblemImpl(ViewProblem.Callback callback)
    {
        super();
        this.callback = callback;
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
        if (callback == null)
            return;

        RecordRepo recordRepo = this.context.getRecordRepo();
        ContextTree tree = this.context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        final Problem problem = treeParser.getCurrentProblemContext();

        if (problem == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVPNoContext();
                }
            });

            return;
        }

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onVPSuccessDetails(problem.getTitle(), problem.getDescription(), problem.getStartDate());
            }
        });

        if (problem.isRecordsEmpty()) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onVPNoRecords();
                }
            });

            return;
        }

        ArrayList<String> recordIds = problem.getRecordIds();
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
                callback.onVPSuccess(records);
            }
        });
    }
}
