package cmput301f18t18.health_detective.domain.interactors;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Geolocation;

public interface ViewRecord extends Interactor {
    interface Callback {
        void onVRInvalidRecord();
        void onVRSuccessDetails(String title, String comment, Date date, Geolocation geolocation);
        void onVRSuccessImages(ArrayList<DomainImage> images);
        void onVRBodyOne(DomainImage bodylocationOne);
        void onVRBodyTwo(DomainImage bodylocationTwo);
        void onVRNoImages();
    }
}
