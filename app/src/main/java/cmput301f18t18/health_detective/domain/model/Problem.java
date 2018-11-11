package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.Date;

public class Problem implements Searchable  {
    // problemID needs to be unique across all patients
    private int problemID;
    private String title;
    private Date startDate;
    private String description;
    private ArrayList<Record> records;

    public Problem() {
        records = new ArrayList<>();
    }

    public Problem(int PUID) {
        this();
        this.problemID = PUID;
    }

    public int getproblemID() {
        return problemID;
    }

    public void setdescription(String description) {
        description = description;
    }

    public String getdescription() {
        return description;
    }

    public void addRecord(Record record) {
        records.add(record);
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
        return (this.problemID ==  problem.getproblemID());
    }
}
