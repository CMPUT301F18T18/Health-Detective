package cmput301f18t18.health_detective.presentation.view.activity.presenters;



import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecord;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.ViewProblem;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.DeleteRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetLoggedInUserImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.PutContext;
import cmput301f18t18.health_detective.domain.interactors.impl.ViewProblemImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Record;

public class RecordListPresenter implements ViewProblem.Callback, CreateRecord.Callback, DeleteRecord.Callback, GetLoggedInUser.Callback {

    private View view;

    @Override
    public void onVPSuccess(ArrayList<Record> records) {
        this.view.onRecordListUpdate(records);
    }

    @Override
    public void onVPSuccessDetails(String title, String description, Date date) {

    }

    @Override
    public void onVPNoRecords() {
        this.view.onRecordListUpdate(new ArrayList<Record>());
    }

    @Override
    public void onVPNoContext() {

    }

    @Override
    public void onGLIUNoUserLoggedIn() {

    }

    @Override
    public void onGLIUPatient(Patient patient) {
        this.view.onRecordListUserId(patient.getUserId());
        this.view.onPView(patient);
    }

    @Override
    public void onGLIUCareProvider(CareProvider careProvider) {
        this.view.onRecordListUserId(careProvider.getUserId());
        this.view.onCPView(careProvider);
    }

    @Override
    public void onCRSuccess(Record record) {
        new PutContext(record).execute();

        this.view.onCreateRecord();
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

    public interface View {
        void onRecordView();
        void onRecordListUpdate(ArrayList<Record> recordList);
        void onRecordDeleted(Record record);
        void onCreateRecord();
        void onRecordListUserId(String userId);
        void onDeleteRecordFail();
        void onCPView(CareProvider careProvider);
        void onPView(Patient patient);
    }

    public RecordListPresenter(View view)
    {
        this.view = view;

        new GetLoggedInUserImpl(this).execute();
    }

    /**
     * Methdd that calls on the interactor that gets all the records for the current user problem
     */
    public void getUserRecords(){
        ViewProblem viewProblem = new ViewProblemImpl(
                this);

        viewProblem.execute();
    }

    /**
     * Method that calls on interactor that creates a new record
     * @param recordTitle record title
     * @param recordComment record comment
     * @param recordDate record date
     */
    public void createUserRecord(String recordTitle, String recordComment, Date recordDate, Geolocation geolocation){

        CreateRecord createRecord = new CreateRecordImpl(
                this,
                recordTitle,
                recordComment,
                recordDate,
                geolocation
        );

        createRecord.execute();
    }

    /**
     * Method that calls on interactor that will delete the chosen record
     * @param record current record that is being deleted
     */
    public void deleteUserRecords(Record record){
        DeleteRecord deleteRecord = new DeleteRecordImpl(
                this,
                record
        );

        deleteRecord.execute();
    }

    public void onView(Record record) {
        new PutContext(record).execute();

        view.onRecordView();
    }
}
