package cmput301f18t18.health_detective.presentation.view.activity.presenters;


import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.interactors.EditRecord;
import cmput301f18t18.health_detective.domain.interactors.GetLoggedInUser;
import cmput301f18t18.health_detective.domain.interactors.ViewRecord;
import cmput301f18t18.health_detective.domain.interactors.impl.EditRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetLoggedInUserImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.Logout;
import cmput301f18t18.health_detective.domain.interactors.impl.ViewRecordImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Record;


public class RecordViewPresenter implements ViewRecord.Callback, EditRecord.Callback, GetLoggedInUser.Callback {

    private View view;

    @Override
    public void onVRInvalidRecord() {

    }

    @Override
    public void onVRSuccessDetails(String title, String comment, Date date, Geolocation geolocation) {
        this.view.onRecordDetails(title, comment, date, geolocation);
    }

    @Override
    public void onVRSuccessImages(ArrayList<DomainImage> images) {
        view.onRecordImages(images);
    }

    @Override
    public void onVRBodyOne(DomainImage bodylocationOne) {
        view.displayBodyimageOne(bodylocationOne);
    }

    @Override
    public void onVRBodyTwo(DomainImage bodylocationTwo) {
        view.displayBodyimageTwo(bodylocationTwo);
    }

    @Override
    public void onVRNoImages() {
        view.noImages();
    }

    public interface View {
        void onRecordImages(ArrayList<DomainImage> images);
        void onRecordDetails(String title, String comment, Date date, Geolocation geolocation);
        void makeToast(String msg, int length);
        void onGetPatient(Patient patient);
        void onGetCP(CareProvider careProvider);
        void displayBodyimageOne(DomainImage image);
        void displayBodyimageTwo(DomainImage image);
        void onLogout();
        void noImages();
    }


    public RecordViewPresenter(View view){
        new GetLoggedInUserImpl(this).execute();
        this.view = view;
    }

    /**
     * Method that calls on interactor that edits the chosen record
     * @param recordTitle new record title
     * @param recordComment new record comment
     * @param recordDate new record date
     */

    public void editUserRecord(String recordTitle, String recordComment, Date recordDate, Geolocation geolocation, DomainImage bodylocationOne, DomainImage bodylocationTwo){
        EditRecord editRecord = new EditRecordImpl(
                this,
                recordTitle,
                recordComment,
                recordDate,
                geolocation,
                bodylocationOne,
                bodylocationTwo
        );
        editRecord.execute();
    }

    public void onResume() {
        new ViewRecordImpl(this).execute();
    }

    @Override
    public void onERSuccess(Record record) {
        this.view.onRecordDetails(record.getTitle(), record.getComment(), record.getDate(), record.getGeolocation());
        this.view.makeToast("Record is edited", Toast.LENGTH_SHORT);
    }

    @Override
    public void onEREmptyTitle() {

    }

    @Override
    public void onERNoDateProvided() {

    }

    @Override
    public void onERNoGeolocationProvided() {

    }

    @Override
    public void onERInvalidPermissions() {

    }

    @Override
    public void onGLIUNoUserLoggedIn() {

    }

    @Override
    public void onGLIUPatient(Patient patient) {
        this.view.onGetPatient(patient);
    }

    @Override
    public void onGLIUCareProvider(CareProvider careProvider) {
        this.view.onGetCP(careProvider);
    }

    public void onLogout() {
        new Logout().execute();

        view.onLogout();
    }

}
