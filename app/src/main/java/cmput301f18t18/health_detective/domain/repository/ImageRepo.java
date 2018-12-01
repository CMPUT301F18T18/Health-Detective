package cmput301f18t18.health_detective.domain.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.DomainImage;

public interface ImageRepo {
    void insertImage(DomainImage DomainImage);
    DomainImage retrieveImageById(String id);
    ArrayList<DomainImage> retrieveImagesByIds(ArrayList<String> id);
    void deleteImage(DomainImage DomainImage);
}
