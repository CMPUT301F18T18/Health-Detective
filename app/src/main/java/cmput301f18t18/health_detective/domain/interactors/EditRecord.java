package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Record;

public interface EditRecord extends Interactor {
    public interface Callback {
        void onEPSuccess(Record record);
        void onEPFail();
    }
}