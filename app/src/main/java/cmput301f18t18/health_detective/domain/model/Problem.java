package cmput301f18t18.health_detective.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.util.Id;

/**
 *
 */
public class Problem {
    private String problemId;
    private String title;
    private Date startDate;
    private String description;
    private HashSet<String> records;

    public Problem() {
        records = new HashSet<>();
        DomainContext context = DomainContext.getInstance();
        String newId = Id.genUniqueId(context.getSecureRandom());

        this.problemId = newId;
        this.setTitle(null);
        this.setDescription(null);
        this.setStartDate(new Date());
    }

    public Problem(String id) {
        this();
        this.problemId = id;
    }

    public Problem(String title, String description) {
        this();
        this.setTitle(title);
        this.setDescription(description);
    }

    public Problem(String title, String description, Date startDate) {
        this(title, description);
        this.setStartDate(startDate);
    }

    public Problem(String problemId, String title, String description, Date startDate) {
        this(title, description, startDate);

        this.problemId = problemId;
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

    public String getProblemId() {
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

    public void addRecord(String recordId) {
        records.add(recordId);
    }

    public void removeRecord(Record record) {
        records.remove(record.getRecordId());
    }

    public void removeRecord(String recordId) {
        records.remove(recordId);
    }

    public boolean isRecordsEmpty() {
        return records.isEmpty();
    }

    public ArrayList<String> getRecordIds() {
        ArrayList<String> recordIds = new ArrayList<>();

        if (this.isRecordsEmpty()) {
            return recordIds;
        }

        for (String recordId: this.records) {
            recordIds.add(recordId);
        }

        return  recordIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Problem))
            return false;

        Problem problem = (Problem) o;
        return (this.problemId.equals(problem.getProblemId()));
    }
}
