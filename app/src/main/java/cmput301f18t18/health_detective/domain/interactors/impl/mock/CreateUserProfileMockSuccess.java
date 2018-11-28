package cmput301f18t18.health_detective.domain.interactors.impl.mock;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.CreateUserProfile;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class CreateUserProfileMockSuccess extends AbstractInteractor implements CreateUserProfile {

    private CreateUserProfile.Callback callback;
    private UserRepo userRepo;
    private String userId;
    private String email;
    private String phoneNumber;
    private boolean isCareProvider;

    public CreateUserProfileMockSuccess(ThreadExecutor threadExecutor, MainThread mainThread,
                                        CreateUserProfile.Callback callback, UserRepo userRepo,
                                        String userId, String email, String phoneNumber, boolean isCareProvider)
    {
        super();
        this.callback = callback;
        this.userRepo = userRepo;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isCareProvider = isCareProvider;
    }

    @Override
    public void run() {

        if (isCareProvider){
            final CareProvider careProvider = new CareProvider(
                    this.userId,
                    this.phoneNumber,
                    this.email
            );

            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onCUPCareProviderSuccess(careProvider);
                }
            });
        }

        final Patient patient = new Patient(
                this.userId,
                this.phoneNumber,
                this.email
        );

        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCUPPatientSuccess(patient);
            }
        });
    }
}
