package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.domain.model.Photo;
import cmput301f18t18.health_detective.domain.model.Record;

public interface CreateRecord extends Interactor {
    interface Callback {
        void onCRSuccess();
        void onCRNullTitle();
        void onCRFail();
    }
}