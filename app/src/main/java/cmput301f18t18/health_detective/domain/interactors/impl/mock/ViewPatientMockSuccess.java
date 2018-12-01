package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.ViewPatient;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class ViewPatientMockSuccess extends AbstractInteractor implements ViewPatient {

    private ViewPatient.Callback callback;
    private ProblemRepo problemRepo;
    private Patient patient;

    public ViewPatientMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                  ViewPatient.Callback callback, ProblemRepo problemRepo,
                                  Patient patient)
    {
        super();
        this.callback = callback;
        this.problemRepo = problemRepo;
        this.patient = patient;
    }

    @Override
    public void run() {
        final ArrayList<Problem> problems = new ArrayList<>();

        problems.add(new Problem(
                "Example Problem 1",
                "This problem is an example and if you see this in production please create an issue"
        ));

        problems.add(new Problem(
                "Example Problem 2",
                "This problem is an example and if you see this in production please create an issue"
        ));

        problems.add(new Problem(
                "Example Problem 3",
                "This problem is an example and if you see this in production please create an issue"
        ));

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGPSuccess(problems);
            }
        });
    }
}