package cmput301f18t18.health_detective.data.transaction.base;

import android.database.sqlite.SQLiteDatabase;

import com.searchly.jestdroid.JestDroidClient;

public abstract class AbstractRepo {
    protected JestDroidClient client;
    protected String elasticIndex;
    protected SQLiteDatabase db;
    protected Boolean online;

    public AbstractRepo(JestDroidClient client, String index, SQLiteDatabase db, Boolean online) {
        this.client = client;
        this.db = db;
        this.elasticIndex = index;
        this.online = online;
    }

    public abstract void insert();
    public abstract void update();
    public abstract void delete();
}
