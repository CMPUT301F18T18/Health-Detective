package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

public class Problem implements Searchable  {
    // problemID needs to be unique across all patients
    private int problemID;
    private String title;
    private Date startDate;
    private String description;
    private ArrayList<Record> records;

    @JestId
    private String problemJestId;

    public Problem() {
        records = new ArrayList<>();
    }

    public Problem(int PUID) {
        this();
        this.problemID = PUID;
    }

    public int getProblemID() {
        return problemID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
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
        return (this.problemID ==  problem.getProblemID());
    }

    public void setProblemJestId(String id) { this.problemJestId = id; }

    public String getProblemJestId() { return this.problemJestId; }
}
