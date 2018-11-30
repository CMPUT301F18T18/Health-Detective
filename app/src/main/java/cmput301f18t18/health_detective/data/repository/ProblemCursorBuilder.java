package cmput301f18t18.health_detective.data.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProblemCursorBuilder implements QueryBuilder {

    private static final String TABLE_NAME = "Problems";

    SQLiteDatabase db;

    ProblemCursorBuilder(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Cursor retrieveQuery(String problemId) {

        return db.query(
                TABLE_NAME,
                null,
                "problemId = ?",
                new String[] {problemId},
                null,
                null,
                null);
    }

    // Warning: Untested
    @Override
    public Cursor retrieveListQuery(ArrayList<String> problemIds) {
        String[] problemIdsArray = problemIds.toArray(new String[0]);

        return db.query(
                TABLE_NAME,
                null,
                "problemId = ?",
                problemIdsArray,
                null,
                null,
                null
        );
    }
}
