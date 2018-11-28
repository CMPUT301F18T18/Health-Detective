package cmput301f18t18.health_detective.domain.model.context.base;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import cmput301f18t18.health_detective.domain.executor.MainThread;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.model.context.tree.base.ContextTree;
import cmput301f18t18.health_detective.domain.repository.BodyLocationRepo;
import cmput301f18t18.health_detective.domain.repository.ImageRepo;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;

public class DomainContext {
    private static DomainContext ourInstance = null;
    private SecureRandom secureRandom = null;
    private ThreadExecutor threadExecutor = null;
    private MainThread mainThread = null;
    private UserRepo userRepo = null;
    private ProblemRepo problemRepo = null;
    private RecordRepo recordRepo = null;
    private BodyLocationRepo bodyLocationRepo = null;
    private ImageRepo imageRepo = null;

    private ContextTree contextTree;

    public static DomainContext getInstance() {
        if (ourInstance == null) {
            ourInstance = new DomainContext();
        }

        return ourInstance;
    }

    private DomainContext() {}

    public static void init_ONLY_CALL_START(ThreadExecutor threadExecutor, MainThread mainThread,
                                     UserRepo userRepo, ProblemRepo problemRepo, RecordRepo recordRepo,
                                     BodyLocationRepo bodyLocationRepo, ImageRepo imageRepo) {
        // If already initilized return
        if (ourInstance != null) {
            return;
        }

        DomainContext context = DomainContext.getInstance();
        context.contextTree = new ContextTree();

        try {
            context.secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        context.threadExecutor = threadExecutor;
        context.mainThread = mainThread;
        context.userRepo = userRepo;
        context.problemRepo = problemRepo;
        context.recordRepo = recordRepo;
        context.bodyLocationRepo = bodyLocationRepo;
        context.imageRepo = imageRepo;
    }

    public SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    public ThreadExecutor getThreadExecutor() {
        return threadExecutor;
    }

    public MainThread getMainThread() {
        return mainThread;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public ProblemRepo getProblemRepo() {
        return problemRepo;
    }

    public RecordRepo getRecordRepo() {
        return recordRepo;
    }

    public BodyLocationRepo getBodyLocationRepo() {
        return bodyLocationRepo;
    }

    public ImageRepo getImageRepo() {
        return imageRepo;
    }

    public ContextTree getContextTree() {
        return contextTree;
    }
}
