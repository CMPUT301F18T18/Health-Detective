package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

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
    private Date date;

    /**
     * First constructor for EditRecordImpl
     * @param recordRepo the repository where records are stored
     * @param recordToEdit the record that is being edited
     * @param title the title of the record that is being edited
     * @param comment the description of the record being edited
     * @param date the date assigned to the record being edited
     */
    public EditRecordImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                          EditRecord.Callback callback, RecordRepo recordRepo,
                          Record recordToEdit, String title, String comment, Date date)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.recordRepo = recordRepo;
        this.recordtoEdit = recordToEdit;
        this.title = title;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Second constructor for EditRecordImpl
     * @param recordRepo the repository where records are stored
     * @param recordToEdit the record that is being edited
     * @param title the title of the record that is being edited
     * @param comment the description of the record being edited
     */
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
        this.date = recordToEdit.getDate();
    }

    /**
     * Third constructor for EditRecordImpl
     * @param recordRepo the repository where records are stored
     * @param recordToEdit the record that is being edited
     * @param comment the description of the record being edited
     */
    public EditRecordImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                          EditRecord.Callback callback, RecordRepo recordRepo,
                          Record recordToEdit, String comment)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.recordRepo = recordRepo;
        this.recordtoEdit = recordToEdit;
        this.title = recordToEdit.getTitle();
        this.comment = comment;
        this.date = recordToEdit.getDate();
    }

    /**
     * Main run method for EditRecordImpl. This method contains all the specific
     * business logic needed for the interactor. The main jobs of this method are to
     * make sure the title for the record is not missing as well as the date, and
     * then to correctly set the comment and edit the record.
     */
    @Override
    public void run() {
        // Missing title
        if (this.title == null || this.title.isEmpty()) {
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onEREmptyTitle();
                }
            });

            return;
        }
        // Missing date
        if (this.date == null) {
            this.mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onERNoDateProvided();
                }
            });

            return;
        }

        if (this.comment == null) {
            this.comment = "";
        }

        this.recordtoEdit.setTitle(this.title);
        this.recordtoEdit.setComment(this.comment);
        this.recordtoEdit.setDate(this.date);

        this.recordRepo.updateRecord(this.recordtoEdit);

        // Record added
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onERSuccess(recordtoEdit);
            }
        });
    }
}
