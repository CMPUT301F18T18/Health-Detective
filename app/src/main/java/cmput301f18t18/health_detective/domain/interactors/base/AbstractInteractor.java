package cmput301f18t18.health_detective.domain.interactors.base;

import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.MainThread;

public abstract class AbstractInteractor implements Interactor {
    protected volatile boolean isExecuting = false;
    protected volatile boolean isStoped = false;
    protected ThreadExecutor threadExecutor;
    protected MainThread mainThread;

    public AbstractInteractor(ThreadExecutor threadExecutor, MainThread mainThread) {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
    }

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