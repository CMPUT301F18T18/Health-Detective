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

public class CreateRecordImpl extends AbstractInteractor implements CreateRecord {

    private CreateRecord.Callback callback;
    private ProblemRepo problemRepo;
    private RecordRepo recordRepo;
    private Problem problem;
    private String recordTitle;
    private String recordComment;
    private Date date;
    private String authorId;

    /**
     * Constructor for CreateRecordImpl
     * @param problemRepo the repository where problems are stored
     * @param recordRepo the repository where records are stored
     * @param problem the problem the created record is getting added to
     * @param recordTitle the title of the created record
     * @param recordComment the description of the created record
     * @param date the date chosen for the created record
     * @param authorId the author that created the record
     */
    public CreateRecordImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                            CreateRecord.Callback callback, ProblemRepo problemRepo, RecordRepo recordRepo,
                            Problem problem, String recordTitle, String recordComment, Date date, String authorId)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.recordRepo = recordRepo;
        this.problem = problem;
        this.recordTitle = recordTitle;
        this.recordComment = recordComment;
        this.date = date;
        this.authorId = authorId;
    }

    /**
     * Main run method for CreateRecordImpl. This method contains all the specific
     * business logic needed for the interactor. The main jobs of this method are to
     * make sure the record title is not empty, make sure to description is properly
     * set, to set date for the record if one has not been already, and then of course
     * to create the record.
     */
    @Override
    public void run() {
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
