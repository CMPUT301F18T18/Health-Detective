package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetAssignedPatients;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class GetAssignedPatientsMockSuccess extends AbstractInteractor implements GetAssignedPatients {

    private GetAssignedPatients.Callback callback;
    private UserRepo userRepo;
    private CareProvider careProvider;

    public GetAssignedPatientsMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                          GetAssignedPatients.Callback callback, UserRepo userRepo,
                                          CareProvider careProvider)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.careProvider = careProvider;
    }

    @Override
    public void run() {

    }
}
