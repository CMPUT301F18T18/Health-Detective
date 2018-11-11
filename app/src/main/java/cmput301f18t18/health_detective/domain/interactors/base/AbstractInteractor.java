package cmput301f18t18.health_detective.domain.interactors.base;

import cmput301f18t18.health_detective.domain.executor.ThreadExecutorContract;
import cmput301f18t18.health_detective.domain.executor.MainThreadContract;

public abstract class AbstractInteractor implements InteractorContract {
    protected volatile boolean isExecuting;
    protected volatile boolean isStoped;
    protected ThreadExecutorContract threadExecutor;
    protected MainThreadContract mainThread;

    /**
     * Method contains the business logic of the interactor
     * 
     * NOTE: Calling this method directly will run the business logic on the 
     *       thread it's being called from. IT'S HIGHLY RECOMMENDED that the 
     *       interactor is invoked using the execute() method as this places 
     *       the operation on a background thread. 
     */
    public abstract void run();

    public void cancel() {
        this.isExecuting = false;
        this.isStoped = true;
    }

    public boolean isRunning() {
        return this.isExecuting;
    }

    public void onFinished() {
        this.isStoped = false;
        this.isExecuting = false;
    }

    @Override
    public void execute() {
        this.isExecuting = true;
        this.threadExecutor.execute(this);
    }
}