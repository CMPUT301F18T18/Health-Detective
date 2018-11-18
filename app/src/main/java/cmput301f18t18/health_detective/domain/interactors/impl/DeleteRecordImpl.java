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

    @Override
    public void run() {
        // Need check for problem
        //Record cannot be found
        if(recordRepo.retrieveRecordById(record.recordId) == null){
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onDRNotFound();
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
