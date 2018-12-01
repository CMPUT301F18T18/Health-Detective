package cmput301f18t18.health_detective;

import android.app.Application;

import cmput301f18t18.health_detective.data.DbController;
import cmput301f18t18.health_detective.data.OLD.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.repository.mock.ImageRepoMock;

public class AndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DbController.getInstance().init_ONLY_CALL_START(getApplicationContext()); // May or may not work, we'll see

        DomainContext.init_ONLY_CALL_START(
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                DbController.getInstance(),
                DbController.getInstance(),
                DbController.getInstance(),
                null,
                new ImageRepoMock());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        DbController.getInstance().closeDB_ONLY_CALL_END();
    }
}
