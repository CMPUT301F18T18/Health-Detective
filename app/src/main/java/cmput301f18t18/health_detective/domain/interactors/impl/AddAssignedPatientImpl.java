package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class AddAssignedPatientImpl extends AbstractInteractor implements AddAssignedPatient {

    private AddAssignedPatient.Callback callback;
    private UserRepo userRepo;
    private CareProvider careProvider;
    private String patientId;

    public AddAssignedPatientImpl(ThreadExecutor threadExecutor, MainThread mainThread,
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
        Patient patientToAdd;

        // UserId invalid
        if (!User.isValidUserId(patientId)) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onAAPNotValidUserId();
                }
            });

            return;
        }

        // Patient already assigned
        if (careProvider.hasPatient(patientId)) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onAAPPatientAlreadyAssigned();
                }
            });

            return;
        }

        patientToAdd = userRepo.retrievePatientById(patientId);

        // Patient does not exist
        if (patientToAdd == null) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onAAPPatientDoesNotExist();
                }
            });

            return;
        }

        // Add patient to careProvider and add to repo
        careProvider.addPatient(patientToAdd);
        userRepo.updateUser(careProvider);

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onAAPSuccess();
            }
        });
    }
}