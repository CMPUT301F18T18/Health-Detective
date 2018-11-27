package cmput301f18t18.health_detective.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import cmput301f18t18.health_detective.domain.model.interfaces.Searchable;

/**
 *
 */
public class Problem implements Searchable, Serializable {
    private static final long serialVersionUID = 1L;
    private int problemId;
    private String title;
    private Date startDate;
    private String description;
    private HashSet<Integer> records;

    public Problem() {
        records = new HashSet<>();
        Date createDate = new Date();
        this.problemId = createDate.hashCode();
        this.setTitle(null);
        this.setDescription(null);
        this.setStartDate(createDate);
    }

    public Problem(int problemId, String title, String description) {
        records = new HashSet<>();
        Date createDate = new Date();
        this.problemId = problemId;
        this.setTitle(title);
        this.setDescription(description);
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

    public Problem(String title, String description, Date startDate) {
        records = new HashSet<>();
        Date createDate = new Date();
        this.problemId = createDate.hashCode();
        this.setTitle(title);
        this.setDescription(description);
        this.setStartDate(startDate);
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

    public Date getStartDate() {
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

    public boolean isRecordsEmpty() {
        return records.isEmpty();
    }

    public ArrayList<Integer> getRecordIds() {
        ArrayList<Integer> recordIds = new ArrayList<>();

        if (this.isRecordsEmpty()) {
            return recordIds;
        }

        for (Integer recordId: this.records) {
            recordIds.add(recordId);
        }

        return  recordIds;
    }

    /**
     * Method from searchable to find problems related to bodypart
     * Needs to search records for this.
     *
     * @param bodyPart The bodypart to search for
     * @return         True if problem relates to bodypart
     */
    @Override
    public boolean containsBodyPart(BodyPart bodyPart) {
        return false;
    }

    /**
     * Method from searchable to find problems related to keywords
     * Needs to search records for this.
     *
     * @param keywords The list of keywords to search for
     * @return         True if problem relates to keywords
     */
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
        return (this.problemId == problem.getProblemID());
    }
}
