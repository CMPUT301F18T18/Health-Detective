package cmput301f18t18.health_detective.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class DbController implements ProblemRepo {

    private static final DbController ourInstance = new DbController();

    private SQLiteDatabase db;

    public static DbController getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        db = new DbHelper(context).getWritableDatabase();
    }

    private DbController() { }

    // TODO: Add record stuff
    @Override
    public void insertProblem(Problem problem) {
        ContentValues values = new ContentValues();
        values.put("problemId", problem.getProblemID());
        values.put("title", problem.getTitle());
        values.put("description", problem.getDescription());
        values.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problem.getStartDate()));
        db.insert("Problems", null, values);
    }

    @Override
    public void updateProblem(Problem problem) { }

    // TODO: Add record stuff
    @Override
    public Problem retrieveProblemById(Integer problemID) {
        Cursor cursor = new ProblemQueryBuilder(db).retrieveQuery(Integer.toString(problemID));
        cursor.moveToNext();
        Log.d("DBC:retrieveProblemById", cursor.getString(3));
        Problem ret;
        try {
            ret = new Problem(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(3))
            );
        }
        catch (ParseException e) {
            Log.d("DBC:retrieveProblemById", "Parse exception", e);
            ret = null;
        }
        return ret;
    }

    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<Integer> problemID) {return null;}

    @Override
    public void deleteProblem(Problem problem) {}


}
