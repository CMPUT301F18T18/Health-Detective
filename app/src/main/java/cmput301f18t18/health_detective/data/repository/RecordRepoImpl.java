package cmput301f18t18.health_detective.data.repository;

import android.database.sqlite.SQLiteDatabase;

import com.searchly.jestdroid.JestDroidClient;

import cmput301f18t18.health_detective.domain.model.Record;

public class RecordRepoImpl extends AbstractRepo {

    private Record record;
    private String recordId;

    RecordRepoImpl(JestDroidClient client, SQLiteDatabase db, Record record) {
        super(client, db);
        this.record = record;
        this.recordId = Integer.toString(record.getRecordId());
    }

    RecordRepoImpl(JestDroidClient client, SQLiteDatabase db, String id) {
        super(client, db);
        this.recordId = id;
    }

    @Override
    void insert() {

    }

    @Override
    void update() {

    }

    @Override
    void delete() {

    }
}
