package cmput301f18t18.health_detective.domain.interactors.impl;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.interactors.GetProblems;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class GetProblemsImpl extends AbstractInteractor implements GetProblems {

    private GetProblems.Callback callback;
    private UserRepo userRepo;
    private Patient patient;

    public GetProblemsImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                           GetProblems.Callback callback, UserRepo userRepo,
                           Patient patient)
    {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepo = userRepo;
        this.patient = patient;
    }

    @Override
    public void run() {
        // Logic is unimplemented, so post failed
        this.mainThread.post(new Runnable(){

            @Override
            public void run() {
                callback.onGPFail();
            }
        });
    }
}