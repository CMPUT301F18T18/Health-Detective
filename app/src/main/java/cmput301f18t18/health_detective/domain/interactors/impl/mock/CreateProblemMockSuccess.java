package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.Date;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateProblem;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class CreateProblemMockSuccess extends AbstractInteractor implements CreateProblem {

    private CreateProblem.Callback callback;
    private UserRepo userRepo;
    private ProblemRepo problemRepo;
    private Patient patient;
    private String problemTitle;
    private String problemDescrioption;
    private Date startDate;

    public CreateProblemMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                    CreateProblem.Callback callback, UserRepo userRepo, ProblemRepo problemRepo,
                                    Patient patient, String problemTitle, String problemDescription, Date startDate)
    {
        super();
        this.callback = callback;
        this.userRepo = userRepo;
        this.patient = patient;
        this.problemRepo = problemRepo;
        this.problemTitle = problemTitle;
        this.problemDescrioption = problemDescription;
        this.startDate = startDate;
    }


    @Override
    public void run() {

    }
}
