package cmput301f18t18.health_detective;

import android.app.Application;

import cmput301f18t18.health_detective.data.DbController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.repository.mock.ImageRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.ProblemRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.RecordRepoMock;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;

public class AndroidApplication extends Application {

    UserRepoMock mockUR = new UserRepoMock();
    ProblemRepoMock mockPR = new ProblemRepoMock();
    RecordRepoMock mockRR = new RecordRepoMock();

    @Override
    public void onCreate() {
        super.onCreate();

        UserRepoMock users = new UserRepoMock();
        mockUR.insertUser(new Patient("dalinriches", "(780) 318-0749", "email@.ca"));

        DbController.getInstance().init_ONLY_CALL_START(getApplicationContext()); // May or may not work, we'll see

        DomainContext.init_ONLY_CALL_START(
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                //mockUR,
                DbController.getInstance(),
                //mockPR,
                DbController.getInstance(),
                //mockRR,
                DbController.getInstance(),
                null,
                DbController.getInstance());
                //new ImageRepoMock());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        DbController.getInstance().closeDB_ONLY_CALL_END();
    }
}
