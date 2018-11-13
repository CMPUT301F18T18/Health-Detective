package cmput301f18t18.health_detective.domain.executor.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

public class ThreadExecutorImpl implements ThreadExecutor {

    private static ThreadExecutorImpl executorInstance = null;
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final long KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadExecutorImpl() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE, 
            MAX_POOL_SIZE, 
            KEEP_ALIVE_TIME, 
            TIME_UNIT, 
            WORK_QUEUE);
    }

    public static ThreadExecutorImpl getInstance() {
        if (executorInstance == null) {
            executorInstance = new ThreadExecutorImpl();
        }

        return executorInstance;
    }
    
    @Override
    public void execute(final AbstractInteractor interactor) {
        this.threadPoolExecutor.submit(new Runnable(){
        
            @Override
            public void run() {
                interactor.run();
                interactor.onFinished();
            }
        });
    }
}