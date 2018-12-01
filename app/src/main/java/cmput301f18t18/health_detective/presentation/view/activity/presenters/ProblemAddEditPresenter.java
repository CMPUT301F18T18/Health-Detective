package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import android.content.Context;

import java.util.Date;

import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.interactors.EditProblem;
import cmput301f18t18.health_detective.domain.interactors.impl.CreateProblemImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.EditProblemImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.PutContext;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;

public class ProblemAddEditPresenter implements CreateProblem.Callback, EditProblem.Callback{

    private ProblemAddEditPresenter.AddView addView;

    /**
     * Interface that updates the view inside the activity
     */
    public interface AddView {
        void onCreateProblem();
        void onEditProblem();
    }

    /**
     * This is the presenter constructor that is created when the new ProblemAddEditPresenter is made
     * @param view the view that the presenter is updating
     */
    public ProblemAddEditPresenter (ProblemAddEditPresenter.AddView view) {
        this.addView = view;
    }

    /**
     * This method calls on the interactor that will create a new problem
     * @param problemTitle the problem title the user enters
     * @param problemDescription the problem description the user enters
     * @param startDate the start date the user enters
     */
    public void createNewProblem(String problemTitle, String problemDescription, Date startDate){
        CreateProblem createProblem = new CreateProblemImpl(
                this,
                problemTitle,
                problemDescription,
                startDate
        );
        createProblem.execute();
    }

    /**
     * This method calls on the editProblem interactor to edit the selected problem
     * @param problemTitle this is the new problem title
     * @param problemDescription this is the new problem description
     * @param startDate this is the new problem start date
     */
    public void editUserProblem(String problemTitle, String problemDescription, Date startDate){
        EditProblem editProblem = new EditProblemImpl(
                this,
                problemTitle,
                problemDescription,
                startDate
        );
        editProblem.execute();
    }

    /**
     * This override method is a call back when the new problem is created successfully
     * @param problem current problem created
     */
    @Override
    public void onCPSuccess(Problem problem) {
        this.addView.onCreateProblem();
    }

    @Override
    public void onCPNullTitle() {

    }

    @Override
    public void onCPFail() {

    }

    @Override
    public void onCPNoPatientInScope() {

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

    @Override
    public void onEPInvalidPermissions() {

    }
}
