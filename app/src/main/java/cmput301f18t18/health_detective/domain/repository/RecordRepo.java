package cmput301f18t18.health_detective.domain.repository;

import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.Record;

public interface RecordRepo {
    void insertRecord(Record record);
    void updateRecord(Record record);
    Record retrieveRecordById(String recordID);
    ArrayList<Record> retrieveRecordsById(ArrayList<String> recordID);
    void deleteRecord(Record record);
}
