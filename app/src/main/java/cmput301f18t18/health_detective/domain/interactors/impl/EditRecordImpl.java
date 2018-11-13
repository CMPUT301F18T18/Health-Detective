package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditRecord;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class EditRecordImpl extends AbstractInteractor implements EditRecord {

    private EditRecord.Callback callback;
    private RecordRepo recordRepo;
    private Record recordtoEdit;
    private String title;
    private String comment;

    public EditRecordImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                          EditRecord.Callback callback, RecordRepo recordRepo,
                          Record recordToEdit, String title, String comment)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.recordRepo = recordRepo;
        this.recordtoEdit = recordToEdit;
        this.title = title;
        this.comment = comment;
    }

    @Override
    public void run() {
        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onERFail();
            }
        });
    }
}
