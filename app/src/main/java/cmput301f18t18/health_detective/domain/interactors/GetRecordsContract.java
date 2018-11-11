package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.InteractorContract;
import cmput301f18t18.health_detective.domain.model.Record;

public interface GetRecordsContract extends InteractorContract {
    public interface Callback {
        void onCPSuccess(ArrayList<Record> records);
        void onCPFail();
    }
}