package cmput301f18t18.health_detective.data.repository;

import android.database.sqlite.SQLiteDatabase;

import com.searchly.jestdroid.JestDroidClient;

public abstract class AbstractRepo {
    JestDroidClient client;
    SQLiteDatabase db;

    AbstractRepo(JestDroidClient client, SQLiteDatabase db) {
        this.client = client;
        this.db = db;
    }

    abstract void insert();
    abstract void update();
    abstract void delete();
}
