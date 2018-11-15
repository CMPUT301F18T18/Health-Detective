package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class CreateUserProfileImpl extends AbstractInteractor implements CreateUserProfile {

    private CreateUserProfile.Callback callback;
    private UserRepo userRepo;
    private String userId;
    private String email;
    private String phoneNumber;
    private boolean isCareProvider;

    public CreateUserProfileImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                 CreateUserProfile.Callback callback, UserRepo userRepo,
                                 String userId, String email, String phoneNumber, boolean isCareProvider)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isCareProvider = isCareProvider;
    }

    @Override
    public void run() {
        //Not a unique UserID
        if(userRepo.validateUserIdUniqueness(userId) == false){
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPNotUniqueID();
                }
            });

            return;
        }

        //Create new Care Provider profile
        if (isCareProvider == true) {
            CareProvider newCP = new CareProvider(userId,phoneNumber,email);
            userRepo.insertUser(newCP);
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPCareProviderSuccess(newCP);
                }
            });
        } else {
            //Create new Patient profile
            Patient newPatient = new Patient(userId,phoneNumber,email);
            userRepo.insertUser(newPatient);
            this.mainThread.post(new Runnable() {

                @Override
                public void run() {
                    callback.onCUPPatientSuccess(newPatient);
                }
            });
        }
    }
}
