package cmput301f18t18.health_detective.domain.interactors.impl;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.EditRecord;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

/**
 * The EditRecordImpl class is a class intended to handle the editing of records
 * on the back end.
 */
public class EditRecordImpl extends AbstractInteractor implements EditRecord {

    private EditRecord.Callback callback;
    private String title;
    private String comment;
    private Date date;
    private Geolocation geolocation;


    /**
     * First constructor for EditRecordImpl
     * @param callback
     * @param title the title of the record that is being edited
     * @param comment the description of the record being edited
     * @param date the date assigned to the record being edited
     */
    public EditRecordImpl(EditRecord.Callback callback,
                          String title, String comment, Date date, Geolocation geolocation)
    {
        super();
        this.callback = callback;
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.geolocation = geolocation;
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
        final Record recordtoEdit;
        final RecordRepo recordRepo = this.context.getRecordRepo();
        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        User loggedInUser = treeParser.getLoggedInUser();

        if (loggedInUser instanceof CareProvider) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onERInvalidPermissions();
                }
            });
        }

        recordtoEdit = treeParser.getCurrentRecordContext();

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

        // Missing geolocation
        if (this.geolocation == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onERNoGeolocationProvided();
                }
            });
        }

        if (this.comment == null) {
            this.comment = "";
        }

        recordtoEdit.setTitle(this.title);
        recordtoEdit.setComment(this.comment);
        recordtoEdit.setDate(this.date);
        recordtoEdit.setGeolocation(this.geolocation);

        recordRepo.updateRecord(recordtoEdit);

        // Record added
        this.mainThread.post(new Runnable(){
            @Override
            public void run() {
                callback.onERSuccess(recordtoEdit);
            }
        });
    }
}
