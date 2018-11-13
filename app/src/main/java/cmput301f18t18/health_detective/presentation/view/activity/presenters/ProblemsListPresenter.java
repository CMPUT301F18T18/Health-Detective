package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.DeleteProblemContract;
import cmput301f18t18.health_detective.domain.interactors.GetPatientsProblemsContract;
import cmput301f18t18.health_detective.domain.model.Problem;

public class ProblemsListPresenter implements GetPatientsProblemsContract.Callback, DeleteProblemContract.Callback {
    private Integer problemId;
    private String userId;

    public void deleteProblem(Integer problemId){

    }

    public void getProblems(String userId){

    }
    @Override
    public void onDPSuccess(Problem problem) {

    }

    @Override
    public void onDPFail() {

    }

    @Override
    public void onGPPSuccess(ArrayList<Problem> patientProblems) {

    }

    @Override
    public void onGPPFail() {

    }
}
