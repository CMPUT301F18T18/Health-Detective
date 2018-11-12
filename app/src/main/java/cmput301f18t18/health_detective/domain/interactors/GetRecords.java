package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Record;

public interface GetRecords extends Interactor {
    public interface Callback {
        void onCPSuccess(ArrayList<Record> records);
        void onCPFail();
    }
}