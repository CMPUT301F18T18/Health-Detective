package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecord;
import cmput301f18t18.health_detective.domain.interactors.ViewProblem;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.DeleteRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.ViewProblemImpl;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;

public class RecordListPresenter implements ViewProblem.Callback, CreateRecord.Callback, DeleteRecord.Callback {

    private View view;

    //private RecordListAdapter adapter;

    public interface View {
        void onRecordListUpdate(ArrayList<Record> recordList);
        void onRecordDeleted(Record record);
        void onCreateRecord(Record record);
        void onCreateRecordFail();
        void onDeleteRecordFail();
    }

    public RecordListPresenter(View view)
    {
        this.view = view;
    }

    /**
     * Methdd that calls on the interactor that gets all the records for the current user problem
     * @param problem problem that user wants to see all records for
     */
    public void getUserRecords(Problem problem){
        ViewProblem viewProblem = new ViewProblemImpl(
                this,
                problem);

        viewProblem.execute();

    }

    /**
     * Method that calls on interactor that creates a new record
     * @param problem current problem that record is being added to
     * @param recordTitle record title
     * @param recordComment record comment
     * @param recordDate record date
     * @param userId current user that is adding the new record
     */
    public void createUserRecord(Problem problem, String recordTitle, String recordComment, Date recordDate, String userId){

        CreateRecord createRecord = new CreateRecordImpl(
                this,
                recordTitle,
                recordComment,
                recordDate,
                null
        );

        createRecord.execute();
    }

    /**
     * Method that calls on interactor that will delete the chosen record
     * @param problem current problem that record is attached too
     * @param record current record that is being deleted
     */
    public void deleteUserRecords(Problem problem, Record record){
        DeleteRecord deleteRecord = new DeleteRecordImpl(
                this,
                record
        );

        deleteRecord.execute();
    }


    @Override
    public void onGRSuccess(ArrayList<Record> records) {
        this.view.onRecordListUpdate(records);
        //recordListAdapter = new RecordListAdapter(activity, records, activity);
    }

    @Override
    public void onGRNoRecords() {
        this.view.onRecordListUpdate(new ArrayList<Record>());
    }

    @Override
    public void onCRSuccess(Record record) {
        this.view.onCreateRecord(record);
    }

    @Override
    public void onCRNullTitle() {

    }

    @Override
    public void onCRInvalidPermissions() {

    }

    @Override
    public void onCRNoGeolocationProvided() {

    }

    @Override
    public void onDRSuccess(Record record) {
        this.view.onRecordDeleted(record);
    }

    @Override
    public void onDRProblemNotFound() {

    }

    @Override
    public void onDRRecordNotFound() {

    }

    @Override
    public void onDRFail() {
        this.view.onDeleteRecordFail();

    }
}
