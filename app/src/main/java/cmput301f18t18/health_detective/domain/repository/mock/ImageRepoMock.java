package cmput301f18t18.health_detective.domain.repository.mock;

import java.util.ArrayList;
import java.util.HashMap;

import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.repository.ImageRepo;

public class ImageRepoMock implements ImageRepo {
    private HashMap<String, DomainImage> images = new HashMap<>();
    
    @Override
    public void insertImage(DomainImage domainImage) {
        if (domainImage == null) {
            return;
        }

        this.images.put(domainImage.getImageId(), domainImage);
    }

    @Override
    public DomainImage retrieveImageById(String id) {
        if (id == null) {
            return null;
        }

        return this.images.get(id);
    }

    @Override
    public ArrayList<DomainImage> retrieveImagesByIds(ArrayList<String> id) {
        ArrayList<DomainImage> domainImageList = new ArrayList<>();

        if (id == null || id.isEmpty()) {
            return domainImageList;
        }

        for (DomainImage domainImage: this.images.values()) {
            domainImageList.add(domainImage);
        }

        return domainImageList;
    }

    @Override
    public void deleteImage(DomainImage domainImage) {
        if (domainImage == null) {
            return;
        }

        this.images.remove(domainImage.getImageId());
    }
}
