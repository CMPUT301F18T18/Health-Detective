package cmput301f18t18.health_detective.model.domain;

import java.util.ArrayList;
import java.util.Date;

public class Problem implements Searchable  {
    // ProblemID needs to be unique across all patients
    private int ProblemID;
    private String Title;
    private Date StartDate;
    private String Description;
    private ArrayList<Record> Records;

    @Override
    public boolean containsBodyPart(BodyPart bodyPart) {
        return false;
    }

    @Override
    public boolean containsKeyword(ArrayList<String> keywords) {
        return false;
    }
}
