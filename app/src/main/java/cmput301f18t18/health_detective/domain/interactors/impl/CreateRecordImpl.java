package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

/**
 * The CreateRecordImpl class is a class intended to handle the creation of records
 * on the back end.
 */
public class CreateRecordImpl extends AbstractInteractor implements CreateRecord {

    private CreateRecord.Callback callback;
    private String recordTitle;
    private String recordComment;
    private Geolocation geolocation;
    private Date date;

    /**
     * Constructor for CreateRecordImpl
     * @param callback
     * @param recordTitle the title of the created record
     * @param recordComment the description of the created record
     * @param date the date chosen for the created record
     * @param authorId the author that created the record
     */
    public CreateRecordImpl(CreateRecord.Callback callback,
                            String recordTitle, String recordComment, Date date, Geolocation geolocation)
    {
        super();
        this.callback = callback;
        this.recordTitle = recordTitle;
        this.recordComment = recordComment;
        this.geolocation = geolocation;
        this.date = date;
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
        final Problem problem;
        final User author;

        if(recordTitle == null){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCRNullTitle();
                }
            });

            return;
        }


        ContextTreeParser contextTreeParser = new ContextTreeParser(context.getContextTree());
        problem = contextTreeParser.getCurrentProblemContext();
        author = contextTreeParser.getLoggedInUser();

        if (problem == null || author == null) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onCRInvalidPermissions();
                }
            });

            return;
        }

        if(recordComment == null)
            recordComment = "";

        Record newRecord = new Record(recordTitle,recordComment);

        if(this.date != null){
            newRecord.setDate(this.date);
        }

        // Missing geolocation
        if (this.geolocation == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onCRNoGeolocationProvided();
                }
            });
        }

        newRecord.setGeolocation(geolocation);

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
