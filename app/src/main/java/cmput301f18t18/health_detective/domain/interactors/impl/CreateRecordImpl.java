package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

/**
 * The CreateRecordImpl class is a class intended to handle the creation of records
 * on the back end.
 */
public class CreateRecordImpl extends AbstractInteractor implements CreateRecord {

    private CreateRecord.Callback callback;
    private Problem problem;
    private String recordTitle;
    private String recordComment;
    private Date date;
    private String authorId;

    /**
     * Constructor for CreateRecordImpl
     * @param callback
     * @param problem the problem the created record is getting added to
     * @param recordTitle the title of the created record
     * @param recordComment the description of the created record
     * @param date the date chosen for the created record
     * @param authorId the author that created the record
     */
    public CreateRecordImpl(CreateRecord.Callback callback,
                            Problem problem, String recordTitle, String recordComment, Date date, String authorId)
    {
        super();
        this.callback = callback;
        this.problem = problem;
        this.recordTitle = recordTitle;
        this.recordComment = recordComment;
        this.date = date;
        this.authorId = authorId;
    }

    /**
     * Creates a record and adds it to the database
     *
     * Callbacks:
     *      -Calls onCRNullTitle()
     *          title entered for record creation is not valid
     *
     *      -Calls onCRSuccess(newRecord)
     *          record created successfully and added to database as well as a problem
     */
    @Override
    public void run() {
        final ProblemRepo problemRepo = this.context.getProblemRepo();
        final RecordRepo recordRepo = this.context.getRecordRepo();

        if(recordTitle == null){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCRNullTitle();
                }
            });

            return;
        }

        if(recordComment == null) recordComment = "";
            Record newRecord = new Record(recordTitle,recordComment);

        if(this.date != null){
            newRecord.setDate(this.date);
        }

        //Add record to recordRepo
        recordRepo.insertRecord(newRecord);
        problem.addRecord(newRecord);
        problemRepo.updateProblem(problem);
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onCRSuccess(newRecord);
            }
        });
    }
}
