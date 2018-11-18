package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.interactors.DeleteRecord;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.DeleteProblemImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.DeleteRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetRecordsImpl;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;
import cmput301f18t18.health_detective.presentation.view.activity.PatientRecordViewActivity;
import cmput301f18t18.health_detective.presentation.view.activity.RecordListAdapter;

public class RecordListPresenter implements GetRecords.Callback, CreateRecord.Callback, DeleteRecord.Callback {

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private ProblemRepo problemRepo;
    private RecordRepo recordRepo;
    private Context context;
    private View view;

    //private RecordListAdapter adapter;

    public interface View {
        void onRecordListUpdate(ArrayList<Record> recordList);
        void onRecordDeleted(Record record);
    }

    public RecordListPresenter(View view, ThreadExecutor threadExecutor, MainThread mainThread,
                               ProblemRepo problemRepo, RecordRepo recordRepo)
    {
        this.view = view;
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.problemRepo = problemRepo;
        this.recordRepo = recordRepo;
    }

    public void getUserRecords(Problem problem){
        GetRecords getRecords = new GetRecordsImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.recordRepo,
                problem
        );

        getRecords.execute();

    }

//    public void createUserRecord(Context context, Problem problem, String recordTitle, String recordComment, Date recordDate, String userId){
//        this.context = context;
//
//        CreateRecord createRecord = new CreateRecordImpl(
//                this.threadExecutor,
//                this.mainThread,
//                this,
//                this.problemRepo,
//                this.recordRepo,
//                problem,
//                recordTitle,
//                recordComment,
//                recordDate,
//                userId
//        );
//
//        createRecord.execute();
//    }

    public void deleteUserRecords(Problem problem, Record record){
        DeleteRecord deleteRecord = new DeleteRecordImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.problemRepo,
                this.recordRepo,
                problem,
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
    public void onCRSuccess() {
        //Intent intent = new Intent(context, PatientRecordViewActivity.class);
        //intent.putExtra("RECORD", record);
        Toast.makeText(context, "New Record Added", Toast.LENGTH_SHORT).show();
        //context.startActivity(intent);
    }

    @Override
    public void onCRNullTitle() {
        Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onCRFail() {
        Toast.makeText(context, "record not added", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDRSuccess(Record record) {
        this.view.onRecordDeleted(record);
    }

    @Override
    public void onDRNotFound() {

    }

    @Override
    public void onDRFail() {

    }
}
