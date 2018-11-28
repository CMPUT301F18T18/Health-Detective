package cmput301f18t18.health_detective;

import android.app.Application;

import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;

public class AndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DomainContext.init_ONLY_CALL_START(
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                ElasticSearchController.getInstance(),
                ElasticSearchController.getInstance(),
                ElasticSearchController.getInstance(),
                null,
                null,
                null);
    }
}
