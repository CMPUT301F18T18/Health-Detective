package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecord;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class DeleteRecordImpl extends AbstractInteractor implements DeleteRecord {

    private DeleteRecord.Callback callback;
    private RecordRepo recordRepo;
    private Record record;

    public DeleteRecordImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                            DeleteRecord.Callback callback, RecordRepo problemRepo,
                            Record record)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.recordRepo = problemRepo;
        this.record = record;
    }

    @Override
    public void run() {
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

        //Delete Record
        this.recordRepo.deleteRecord(record);
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onDRSuccess(record);
            }
        });
    }

}
