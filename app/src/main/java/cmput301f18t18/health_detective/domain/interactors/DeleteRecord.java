package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Record;

public interface DeleteRecord extends Interactor {
    interface Callback {
        void onDRSuccess(Record record);
        void onDRProblemNotFound();
        void onDRRecordNotFound();
        void onDRFail();
    }
}
