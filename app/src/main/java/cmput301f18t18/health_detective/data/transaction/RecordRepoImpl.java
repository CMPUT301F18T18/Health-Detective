package cmput301f18t18.health_detective.data.transaction;

import android.database.sqlite.SQLiteDatabase;

import com.searchly.jestdroid.JestDroidClient;

import cmput301f18t18.health_detective.data.transaction.base.AbstractRepo;
import cmput301f18t18.health_detective.domain.model.Record;

public class RecordRepoImpl extends AbstractRepo {

    private Record record;
    private String recordId;

    public RecordRepoImpl(JestDroidClient client, SQLiteDatabase db, Record record) {
        super(client, db);
        this.record = record;
        this.recordId = record.getRecordId();
    }

    public RecordRepoImpl(JestDroidClient client, SQLiteDatabase db, String id) {
        super(client, db);
        this.recordId = id;
    }

    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
