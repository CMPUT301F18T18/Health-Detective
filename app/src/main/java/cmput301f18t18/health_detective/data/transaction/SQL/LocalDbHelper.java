package cmput301f18t18.health_detective.data.transaction.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDbHelper extends SQLiteOpenHelper {

    //TODO: Add other tables and formats

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "test.db";

    private static final String SQL_CREATE_PROBLEMS = "" +
            "CREATE TABLE Problems (" +
            "problemId TEXT PRIMARY KEY, " +
            "title TEXT, " +
            "description TEXT, " +
            "startDate TEXT, " +
            "recordIds TEXT " +
            ")";

    private static final String SQL_CREATE_PATIENTS = "" +
            "CREATE TABLE Patients (" +
            "userId TEXT PRIMARY KEY, " +
            "phone TEXT, " +
            "email TEXT, " +
            "problemIds TEXT" +
            ")";

    private static final String SQL_CREATE_CAREPROVIDERS = "" +
            "CREATE TABLE CareProviders (" +
            "userId TEXT PRIMARY KEY, " +
            "phone TEXT, " +
            "email TEXT, " +
            "patientIds TEXT" +
            ")";

    public LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROBLEMS);
        db.execSQL(SQL_CREATE_PATIENTS);
        db.execSQL(SQL_CREATE_CAREPROVIDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Problems");
        db.execSQL("DROP TABLE IF EXISTS Patients");
        db.execSQL("DROP TABLE IF EXISTS CareProviders");

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Problems");
        db.execSQL("DROP TABLE IF EXISTS Patients");
        db.execSQL("DROP TABLE IF EXISTS CareProviders");

        onCreate(db);
    }
}
