package cmput301f18t18.health_detective.domain.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.images.BodyImage;


public interface BodyImageRepo {
    void insertBodyImage(BodyImage bodyImage);
    void updateBodyImage(BodyImage bodyImage);
    BodyImage retrieveBodyImageById(String id);
    ArrayList<BodyImage> retrieveAllBodyImages();
    void deleteBodyImage();
}
