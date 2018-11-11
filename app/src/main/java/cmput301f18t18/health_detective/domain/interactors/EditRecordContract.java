package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Record;

public interface EditRecordContract extends InteractorContract {
    public interface Callback {
        void onEPSuccess(Record record);
        void onEPFail();
    }
}