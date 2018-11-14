package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import io.searchbox.annotations.JestId;

public class Problem implements Searchable  {

    private int problemId;
    private String title;
    private Date startDate;
    private String description;
    private HashSet<Integer> records;

    @JestId
    private String problemJestId;

    public Problem() {
        records = new HashSet<>();
        Date createDate = new Date();
        this.problemId = createDate.hashCode();
        this.setTitle(null);
        this.setDescription(null);
        this.setStartDate(createDate);
    }

    public Problem(String title, String description) {
        records = new HashSet<>();
        Date createDate = new Date();
        this.problemId = createDate.hashCode();
        this.setTitle(title);
        this.setDescription(description);
        this.setStartDate(createDate);
    }

    public Problem(int problemId, String title, String description, Date startDate) {
        records = new HashSet<>();
        this.problemId = problemId;
        this.setTitle(title);
        this.setDescription(description);
        this.setStartDate(startDate);
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public String getDescription() {
        return description;
    }

    public int getProblemID() {
        return problemId;
    }

    public String getTitle() {
        return title;
    }

    private Date getStartDate() {
        return this.startDate;
    }

    public void addRecord(Record record) {
        records.add(record.getRecordId());
    }

    public void addRecord(Integer recordId) {
        records.add(recordId);
    }

    public void removeRecord(Record record) {
        records.remove(record.getRecordId());
    }

    public void removeRecord(Integer recordId) {
        records.remove(recordId);
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
        return (this.problemId ==  problem.getProblemID());
    }

    public void setProblemJestId(String id) { this.problemJestId = id; }

    public String getProblemJestId() { return this.problemJestId; }
}
