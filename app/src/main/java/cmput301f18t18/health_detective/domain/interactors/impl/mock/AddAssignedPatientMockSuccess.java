package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class AddAssignedPatientMockSuccess extends AbstractInteractor implements AddAssignedPatient {

    private AddAssignedPatient.Callback callback;
    private UserRepo userRepo;
    private CareProvider careProvider;
    private String patientId;


    public AddAssignedPatientMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                         AddAssignedPatient.Callback callback, UserRepo userRepo,
                                         CareProvider careProvider, String patientId)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.careProvider = careProvider;
        this.patientId = patientId;
    }

    @Override
    public void run() {
        // Create new patient with user id
        final Patient patientToAdd = new Patient(patientId);

        // Add patient to careProvider and add to repo
        careProvider.AddPatient(patientToAdd);
        userRepo.updateUser(careProvider);


        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onAAPSuccess();
            }
        });
    }
}
