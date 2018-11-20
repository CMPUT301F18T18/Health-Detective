package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecord;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class DeleteRecordImpl extends AbstractInteractor implements DeleteRecord {

    private DeleteRecord.Callback callback;
    private ProblemRepo problemRepo;
    private RecordRepo recordRepo;
    private Record record;
    private Problem problem;

    /**
     * Constructor for CreateProblemImpl
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
     * Main run method for DeleteRecordImpl. This method contains all the specific
     * business logic needed for the interactor.
     *
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
