package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Record;

public interface CreateRecordContract extends InteractorContract {
    public interface Callback {
        void onCPSuccess(Record record);
        void onCPFail();
    }
}