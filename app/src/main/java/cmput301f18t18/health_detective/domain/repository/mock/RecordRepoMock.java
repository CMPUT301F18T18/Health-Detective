package cmput301f18t18.health_detective.domain.repository.mock;

import java.util.ArrayList;
import java.util.HashMap;

import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.repository.RecordRepo;

public class RecordRepoMock implements RecordRepo {

    private HashMap< Integer, Record> records = new HashMap<>();

    @Override
    public void insertRecord(Record record) {
        if (record == null) {
            return;
        }

        this.records.put(record.getRecordId(), record);
    }

    @Override
    public void updateRecord(Record record) {
        this.insertRecord(record);
    }

    @Override
    public Record retrieveRecordById(Integer recordID) {
        if (recordID == null) {
            return null;
        }

        return this.records.get(recordID);
    }

    @Override
    public ArrayList<Record> retrieveRecordsById(ArrayList<Integer> recordIDs) {
        ArrayList<Record> recordList = new ArrayList<>();

        if (recordIDs == null || recordIDs.isEmpty()) {
            return recordList;
        }

        for (Record record: this.records.values()) {
            recordList.add(record);
        }

        return recordList;
    }

    @Override
    public void deleteRecord(Record record) {
        if (record == null) {
            return;
        }

        this.records.remove(record.getRecordId());
    }
}
