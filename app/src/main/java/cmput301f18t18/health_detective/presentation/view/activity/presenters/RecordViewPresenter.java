package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.view.View;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.EditRecord;
import cmput301f18t18.health_detective.domain.interactors.impl.EditRecordImpl;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;


public class RecordViewPresenter implements EditRecord.Callback{

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private RecordRepo recordRepo;
    private View view;

    public interface View {
        void onEditRecord(Record record);
    }


    public RecordViewPresenter(View view, ThreadExecutor threadExecutor, MainThread mainThread, RecordRepo recordRepo){
        this.view = view;
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.recordRepo = recordRepo;
    }

    /**
     * Method that calls on interactor that edits the chosen record
     * @param record record being edited
     * @param recordTitle new record title
     * @param recordComment new record comment
     * @param recordDate new record date
     */
    public void editUserRecord(Record record, String recordTitle, String recordComment, Date recordDate){
        EditRecord editRecord = new EditRecordImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.recordRepo,
                record,
                recordTitle,
                recordComment,
                recordDate
        );
        editRecord.execute();
    }

    @Override
    public void onERSuccess(Record record) {
        this.view.onEditRecord(record);

    }

    @Override
    public void onEREmptyTitle() {

    }

    @Override
    public void onERNoDateProvided() {

    }

    @Override
    public void onERFail() {

    }

}
