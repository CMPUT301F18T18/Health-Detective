package cmput301f18t18.health_detective.presentation.view.activity.presenters;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.DeleteProblem;
import cmput301f18t18.health_detective.domain.interactors.GetProblems;
import cmput301f18t18.health_detective.domain.model.Problem;

public class ProblemsListPresenter implements GetProblems.Callback, DeleteProblem.Callback {
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
    public void onGPSuccess(ArrayList<Problem> patientProblems) {

    }

    @Override
    public void onGPNoProblems() {

    }
}
