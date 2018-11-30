package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Record;

public interface CreateRecord extends Interactor {
    interface Callback {
        void onCRSuccess(Record record);
        void onCRNullTitle();
        void onCRInvalidPermissions();
        void onCRFail();
    }
}