package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.interactors.ViewPatient;
import cmput301f18t18.health_detective.domain.interactors.impl.DeleteProblemImpl;
import cmput301f18t18.health_detective.domain.interactors.impl.Logout;
import cmput301f18t18.health_detective.domain.interactors.impl.PutContext;
import cmput301f18t18.health_detective.domain.interactors.impl.ViewPatientImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import io.searchbox.core.Get;

public class ProblemsListPresenter implements ViewPatient.Callback, DeleteProblem.Callback {

    private View view;

    @Override
    public void onVPaSuccess(ArrayList<Problem> patientProblems) {
        this.view.onProblemListUpdate(patientProblems);
    }

    @Override
    public void onVPaSuccessDetails(String userId, String email, String phone) {
        this.view.onProblemListUserId(userId);
    }

    @Override
    public void onVPaNoProblems() {
        this.view.onProblemListUpdate(new ArrayList<Problem>());
    }

    @Override
    public void onVPaNoContext() {

    }

    @Override
    public void onDPSuccess(Problem problem) {
        this.view.onProblemDeleted(problem);
    }

    @Override
    public void onDPPatientNotFound() {

    }

    @Override
    public void onDPProblemNotFound() {

    }

    @Override
    public void onDPFail() {

    }


    public interface View {
        void onProblemListUpdate(ArrayList<Problem> problemList);
        void onProblemListUserId(String userId);
        void onProblemDeleted(Problem problem);
        void onViewProblem();
        void onEditProblem();
        void onLogout();
    }

    public ProblemsListPresenter (View view) {
        this.view = view;
    }

    /**
     * Method that calls on interactor that will delete problem
     * @param problem current problem getting deleted
     */
    public void deleteProblem(Problem problem){
        DeleteProblem command = new DeleteProblemImpl(
                this,
                problem
        );

        command.execute();
    }

    /**
     * Method that calls on the interactor that gets all the problems for the current user
     */
    public void getProblems(){
        ViewPatient command = new ViewPatientImpl(
                this);

        command.execute();
    }

    public void onEdit(Problem problem) {
        new PutContext(problem).execute();

        view.onEditProblem();
    }

    public void onView(Problem problem) {
        new PutContext(problem).execute();

        view.onViewProblem();
    }

    public void onLogout() {
        new Logout().execute();

        view.onLogout();
    }

}
