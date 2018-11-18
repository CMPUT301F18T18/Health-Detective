package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.CreateRecord;
import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.interactors.GetRecords;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateRecordImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.GetRecordsImpl;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.PatientProblemsActivity;
import cmput301f18t18.health_detective.presentation.view.activity.PatientRecordViewActivity;
import cmput301f18t18.health_detective.presentation.view.activity.RecordListAdapter;

public class RecordListPresenter implements GetRecords.Callback, CreateRecord.Callback {

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private ProblemRepo problemRepo;
    private RecordRepo recordRepo;
    private Context context;
    private RecordListAdapter recordListAdapter;
    private Activity activity;
    //private RecordListAdapter adapter;

    public RecordListPresenter(ThreadExecutor threadExecutor, MainThread mainThread,
                           ProblemRepo problemRepo, RecordRepo recordRepo, RecordListAdapter recordListAdapter, Activity activity)
    {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.problemRepo = problemRepo;
        this.recordRepo = recordRepo;
        this.recordListAdapter = recordListAdapter;
        this.activity = activity;
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

    public void createUserRecord(Context context, Problem problem, String recordTitle, String recordComment, Date recordDate){
        this.context = context;

        CreateRecord createRecord = new CreateRecordImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.problemRepo,
                this.recordRepo,
                problem,
                recordTitle,
                recordComment,
                recordDate
        );

        createRecord.execute();
    }

    public RecordListAdapter getAdapter(){
        Toast.makeText(activity, "getting Adapter", Toast.LENGTH_SHORT).show();
        return recordListAdapter;
    }

    @Override
    public void onGRSuccess(ArrayList<Record> records) {
        Toast.makeText(activity, "Get Records", Toast.LENGTH_SHORT).show();
        recordListAdapter = new RecordListAdapter(activity, records);
    }

    @Override
    public void onGRNoRecords() {

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
}
