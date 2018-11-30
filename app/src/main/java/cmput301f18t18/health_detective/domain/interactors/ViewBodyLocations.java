package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;

public interface ViewBodyLocations extends Interactor {
    interface Callback {
        void onVBLInvalidPermissions();
        void onVBLNoBodyLocations();
        void onVBLSuccess(ArrayList<BodyLocation> bodyLocations);
    }
}
