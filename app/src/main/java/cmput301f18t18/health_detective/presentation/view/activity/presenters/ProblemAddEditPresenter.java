package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;

import java.util.Date;

import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.interactors.EditProblem;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateProblemImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.EditProblemImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class ProblemAddEditPresenter implements CreateProblem.Callback, EditProblem.Callback{

    private ThreadExecutor threadExecutor;
    private MainThread mainThread;
    private ProblemRepo problemRepo;
    private RecordRepo recordRepo;
    private UserRepo userRepo;
    private Context context;
    private ProblemAddEditPresenter.AddView addView;



    public interface AddView {
        void onCreateProblem();
        void onEditProblem();
    }

    public interface EditView {
        void onEditProblem();

    }

    public ProblemAddEditPresenter (ProblemAddEditPresenter.AddView view, ThreadExecutor threadExecutor, MainThread mainThread,
                                    ProblemRepo problemRepo, UserRepo userRepo) {
        this.addView = view;
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.problemRepo = problemRepo;
        this.userRepo = userRepo;
    }


    public void createNewProblem(Patient patient, String problemTitle, String problemDescription, Date startDate){
        CreateProblem createProblem = new CreateProblemImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.userRepo,
                this.problemRepo,
                patient,
                problemTitle,
                problemDescription,
                startDate
        );
        createProblem.execute();
    }

    public void editUserProblem(Problem problem, String problemTitle, String problemDescription, Date startDate){
        EditProblem editProblem = new EditProblemImpl(
                this.threadExecutor,
                this.mainThread,
                this,
                this.problemRepo,
                problem,
                problemTitle,
                problemDescription,
                startDate
        );
        editProblem.execute();
    }

    @Override
    public void onCPSuccess() {
        this.addView.onCreateProblem();

    }

    @Override
    public void onCPNullTitle() {

    }

    @Override
    public void onCPFail() {

    }

    @Override
    public void onEPSuccess(Problem problem) {
        this.addView.onEditProblem();
    }

    @Override
    public void onEPEmptyTitle() {
    }

    @Override
    public void onEPNoStartDateProvided() {

    }
}
