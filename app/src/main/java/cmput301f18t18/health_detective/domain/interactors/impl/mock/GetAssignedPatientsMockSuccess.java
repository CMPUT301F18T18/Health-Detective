package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetAssignedPatients;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class GetAssignedPatientsMockSuccess extends AbstractInteractor implements GetAssignedPatients {

    private GetAssignedPatients.Callback callback;
    private UserRepo userRepo;
    private CareProvider careProvider;

    public GetAssignedPatientsMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                          GetAssignedPatients.Callback callback, UserRepo userRepo,
                                          CareProvider careProvider)
    {
        super();
        this.callback = callback;
        this.userRepo = userRepo;
        this.careProvider = careProvider;
    }

    @Override
    public void run() {
        final ArrayList<Patient> patients = new ArrayList<>();

        patients.add(new Patient(
                "MockPatient1",
                "780-333-4444",
                "MockPatient1@email.com"
        ));

        patients.add(new Patient(
                "MockPatient2",
                "780-222-1111",
                "MockPatient2@email.com"
        ));

        patients.add(new Patient(
                "MockPatient3",
                "780-555-6666",
                "MockPatient3@email.com"
        ));

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGAPSuccess(patients);
            }
        });
    }
}
