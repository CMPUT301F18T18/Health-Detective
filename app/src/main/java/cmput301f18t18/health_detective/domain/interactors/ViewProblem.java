package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.Record;

public interface ViewProblem extends Interactor {
    interface Callback {
        void onGRSuccess(ArrayList<Record> records);
        void onGRNoRecords();
    }
}