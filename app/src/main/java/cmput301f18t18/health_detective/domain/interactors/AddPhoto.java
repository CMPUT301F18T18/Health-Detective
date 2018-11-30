package cmput301f18t18.health_detective.domain.interactors;

import cmput301f18t18.health_detective.domain.interactors.base.Interactor;
import cmput301f18t18.health_detective.domain.model.DomainImage;

public interface AddPhoto extends Interactor {
    interface Callback {
        void onAPhInvalidPermissions();
        void onAPhNoImage();
        void onAPhSuccess(DomainImage image);
    }
}
