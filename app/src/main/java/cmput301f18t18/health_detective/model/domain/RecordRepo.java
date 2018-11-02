package cmput301f18t18.health_detective.model.domain;

import cmput301f18t18.health_detective.model.domain.Record;

public interface RecordRepo {
    Record getRecord(int recordID);
    void removeRecord(int recordID);
    boolean saveRecord(Record record);
}
