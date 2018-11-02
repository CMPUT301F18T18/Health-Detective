package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;
import java.util.Date;

public class Problem implements SearchableKeywords, SearchableBodyPart {
    // ProblemID needs to be unique across all patients
    private int ProblemID;
    private String Title;
    private Date StartDate;
    private String Description;
    private ArrayList<Record> Records;

    @Override
    public boolean contains(BodyPart bodyPart) {
        return false;
    }

    @Override
    public boolean contains(ArrayList<String> keywords) {
        return false;
    }
}
