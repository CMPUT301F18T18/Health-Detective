package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;

public interface AddBodyLocation extends Interactor {
    interface Callback {
        void onABLNoImage();
        void onABLInvalidCoordinate();
        void onABLEmptyLabel();
        void onABLSuccess(BodyLocation bodyLocation);
        void onABLInvalidPermissions();
    }
}
