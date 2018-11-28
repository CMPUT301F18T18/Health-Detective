package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditRecord;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class EditRecordMockSuccess extends AbstractInteractor implements EditRecord {

    private EditRecord.Callback callback;
    private RecordRepo recordRepo;
    private Record recordtoEdit;
    private String title;
    private String comment;
    private Date date;

    public EditRecordMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                  EditRecord.Callback callback, RecordRepo recordRepo,
                                  Record recordToEdit, String title, String comment, Date date)
    {
        super();
        this.callback = callback;
        this.recordRepo = recordRepo;
        this.recordtoEdit = recordToEdit;
        this.title = title;
        this.comment = comment;
        this.date = date;
    }

    @Override
    public void run() {
        recordtoEdit.setTitle(title);
        recordtoEdit.setComment(comment);
        recordtoEdit.setDate(date);

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onERSuccess(recordtoEdit);
            }
        });
    }
}
