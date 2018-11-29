package cmput301f18t18.health_detective.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //TODO: Add other tables and formats

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "test.db";

    private static final String SQL_CREATE_ENTRIES = "" +
            "CREATE TABLE Problems (" +
                "problemId TEXT PRIMARY KEY, " +
                "title TEXT, " +
                "description TEXT, " +
                "startDate TEXT" +
            ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Problems");

        onCreate(db);
    }
}
