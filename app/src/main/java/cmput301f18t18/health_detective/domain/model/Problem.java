package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.Date;

public class Problem implements Searchable  {
    // ProblemID needs to be unique across all patients
    private int ProblemID;
    private String Title;
    private Date StartDate;
    private String Description;
    private ArrayList<Record> Records;

    public Problem() {
        Records = new ArrayList<>();
    }

    public Problem(int PUID) {
        this();
        this.ProblemID = PUID;
    }

    public int getProblemID() {
        return ProblemID;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void addRecord(Record record) {
        Records.add(record);
    }

    @Override
    public boolean containsBodyPart(BodyPart bodyPart) {
        return false;
    }

    @Override
    public boolean containsKeyword(ArrayList<String> keywords) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Problem))
            return false;

        Problem problem = (Problem) o;
        return (this.ProblemID ==  problem.getProblemID());
    }
}
