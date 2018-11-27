package cmput301f18t18.health_detective.domain.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.BodyLocation;

public interface BodyLocationRepo {
    void insertBodyLocation(BodyLocation bodyLocation);
    void updateBodyLoaction(BodyLocation bodyLocation);
    BodyLocation retrieveBodyLocationById(String id);
    ArrayList<BodyLocation> retrieveAllBodyLocations();
    void deleteBodyLocation();
}
