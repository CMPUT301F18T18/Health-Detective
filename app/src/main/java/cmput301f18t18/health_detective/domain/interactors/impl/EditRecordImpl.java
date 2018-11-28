package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditRecord;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

/**
 * The EditRecordImpl class is a class intended to handle the editing of records
 * on the back end.
 */
public class EditRecordImpl extends AbstractInteractor implements EditRecord {

    private EditRecord.Callback callback;
    private Record recordtoEdit;
    private String title;
    private String comment;
    private Date date;

    /**
     * First constructor for EditRecordImpl
     * @param callback
     * @param recordToEdit the record that is being edited
     * @param title the title of the record that is being edited
     * @param comment the description of the record being edited
     * @param date the date assigned to the record being edited
     */
    public EditRecordImpl(EditRecord.Callback callback,
                          Record recordToEdit, String title, String comment, Date date)
    {
        super();
        this.callback = callback;
        this.recordtoEdit = recordToEdit;
        this.title = title;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Second constructor for EditRecordImpl
     * @param recordToEdit the record that is being edited
     * @param title the title of the record that is being edited
     * @param comment the description of the record being edited
     */
    public EditRecordImpl(EditRecord.Callback callback,
                          Record recordToEdit, String title, String comment)
    {
        super();
        this.callback = callback;
        this.recordtoEdit = recordToEdit;
        this.title = title;
        this.comment = comment;
        this.date = recordToEdit.getDate();
    }

    /**
     * Third constructor for EditRecordImpl
     * @param recordToEdit the record that is being edited
     * @param comment the description of the record being edited
     */
    public EditRecordImpl(EditRecord.Callback callback,
                          Record recordToEdit, String comment)
    {
        super();
        this.callback = callback;
        this.recordtoEdit = recordToEdit;
        this.title = recordToEdit.getTitle();
        this.comment = comment;
        this.date = recordToEdit.getDate();
    }

    /**
     * Edits the information contained in a record and updates the database.
     *
     * Callbacks:
     *      -Calls onEREmptyTitle()
     *          if title is empty (enforcing title)
     *
     *      -Calls onERNoDateProvided()
     *          assigns date if one is not provided
     *
     *      -Calls onERSuccess(recordtoEdit)
     *          if editing record is successful, updates everything containing record
     */
    @Override
    public void run() {
        final RecordRepo recordRepo = this.context.getRecordRepo();

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

        recordRepo.updateRecord(this.recordtoEdit);

        // Record added
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onERSuccess(recordtoEdit);
            }
        });
    }
}
