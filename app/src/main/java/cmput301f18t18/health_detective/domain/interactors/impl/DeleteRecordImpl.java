package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecord;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

/**
 * The DeleteRecordImpl class is a class intended to handle the deletion of records
 * on the back end.
 */
public class DeleteRecordImpl extends AbstractInteractor implements DeleteRecord {

    private DeleteRecord.Callback callback;
    private ProblemRepo problemRepo;
    private RecordRepo recordRepo;
    private Record record;
    private Problem problem;

    /**
     * Constructor for DeleteRecordImpl
     * @param threadExecutor
     * @param mainThread
     * @param callback
     * @param problemRepo the repository where problems are stored
     * @param recordRepo the repository where the records are stored
     * @param problem the problem that the record being deleted belongs to
     * @param record the record being deleted
     */
    public DeleteRecordImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                            DeleteRecord.Callback callback, ProblemRepo problemRepo, RecordRepo recordRepo,
                            Problem problem, Record record)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.recordRepo = recordRepo;
        this.record = record;
        this.problem = problem;
    }

    /**
     * Deletes a record from a problem and the database
     *
     * Callbacks:
     *      -Calls onDRProblemNotFound()
     *          problem owning the record does not exist
     *
     *      -Calls onDRRecordNotFound()
     *          record attempting to be deleted does not exist
     *
     *      -Calls onDRSuccess(record)
     *          if deleting record is successful, updates everything containing record
     */
    @Override
    public void run() {
        // Problem cannot be found
        if(problemRepo.retrieveProblemById(problem.getProblemID()) == null){
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onDRProblemNotFound();
                }
            });

            return;
        }
        //Record cannot be found
        if(recordRepo.retrieveRecordById(record.recordId) == null){
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onDRRecordNotFound();
                }
            });

            return;
        }

        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onDRSuccess(record);
            }
        });

        //Delete Record
        this.recordRepo.deleteRecord(record);
        this.problem.removeRecord(record);
        this.problemRepo.updateProblem(problem);
    }

}
