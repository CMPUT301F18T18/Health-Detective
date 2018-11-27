package cmput301f18t18.health_detective.domain.model;

import cmput301f18t18.health_detective.domain.model.images.BodyImage;
import cmput301f18t18.health_detective.domain.util.Id;

/**
 * Class for BodyLocation data. Probably redundant. Needs to be redone.
 */
public class BodyLocation {

    private String id;
    private String label;
    private Integer xPos;
    private Integer yPos;
    private BodyImage bodyImage;


    public BodyLocation(String label, BodyImage bodyImage, Integer xPos, Integer yPos) {
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        this.setId(newId);
        this.setLabel(label);
        this.setBodyImage(bodyImage);
        this.setMarkerPos(xPos, yPos);
    }

    public BodyLocation(String id, String label, BodyImage bodyImage, Integer xPos, Integer yPos) {
        this(label, bodyImage, xPos, yPos);

        this.setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }

        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getMarkerXPos() {
        return xPos;
    }

    public Integer getMarkerYPos() {
        return yPos;
    }

    public void setMarkerPos(Integer xPos, Integer yPos) {
        if (xPos < 0) {
            xPos = 0;
        }
        else if (yPos < 0) {
            yPos = 0;
        }

        this.xPos = xPos;
        this.yPos = yPos;
    }

    public BodyImage getBodyImage() {
        return bodyImage;
    }

    public void setBodyImage(BodyImage bodyImage) {
        this.bodyImage = bodyImage;
    }
}
