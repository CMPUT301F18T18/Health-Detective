package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Record;

public interface ViewProblem extends Interactor {
    interface Callback {
        void onVPSuccess(ArrayList<Record> records);
        void onVPSuccessDetails(String title, String description, Date date);
        void onVPNoRecords();
        void onVPNoContext();
    }
}