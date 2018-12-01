package cmput301f18t18.health_detective.data.OLD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.data.transaction.SQL.LocalDbHelper;
import cmput301f18t18.health_detective.data.transaction.SQL.ProblemCursorBuilder;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;

public class LocalDbController implements ProblemRepo {

    private static final LocalDbController ourInstance = new LocalDbController();

    private SQLiteDatabase db;

    public static LocalDbController getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        db = new LocalDbHelper(context).getWritableDatabase();
    }

    private LocalDbController() { }

    @Override
    public void insertProblem(Problem problem) {
        ContentValues values = new ContentValues();
        values.put("problemId", problem.getProblemId());
        values.put("title", problem.getTitle());
        values.put("description", problem.getDescription());
        values.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problem.getStartDate()));

        StringBuilder stringBuilder = new StringBuilder();
        for (String id : problem.getRecordIds()) {
            stringBuilder.append(id).append(",");
        }

        String recordIds = stringBuilder.toString();
        if (stringBuilder.length() > 2) { // Trim trailing ","
            recordIds = recordIds.substring(0, recordIds.length()-1);
        }

        values.put("recordIds", recordIds);

        db.insert("Problems", null, values);
    }

    @Override
    public void updateProblem(Problem problem) {
        ContentValues values = new ContentValues();
        values.put("problemId", problem.getProblemId());
        values.put("title", problem.getTitle());
        values.put("description", problem.getDescription());
        values.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problem.getStartDate()));

        StringBuilder stringBuilder = new StringBuilder();
        for (String id : problem.getRecordIds()) {
            stringBuilder.append(id).append(",");
        }

        String recordIds = stringBuilder.toString();
        if (stringBuilder.length() > 2) { // Trim trailing ","
            recordIds = recordIds.substring(0, recordIds.length()-1);
        }

        values.put("recordIds", recordIds);

        db.update("Problems",
                values,
                "problemId = ?",
                new String[] {problem.getProblemId()});
    }

    @Override
    public Problem retrieveProblemById(String problemID) {
        Cursor cursor = new ProblemCursorBuilder(db).retrieveQuery(problemID);
        cursor.moveToNext();
        Problem ret;
        try {
            String problemId = cursor.getString(0);
            String problemTitle = cursor.getString(1);
            String problemDesc = cursor.getString(2);
            Date problemDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(3));

            ret = new Problem(problemId, problemTitle, problemDesc, problemDate);

            for (String recordId : cursor.getString(4).split(",")) {
                ret.addRecord(recordId);
            }
        }
        catch (ParseException e) {
            Log.d("DBC:retrieveProblemById", "Parse exception", e);
            ret = null;
        }
        return ret;
    }

    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<String> problemIds) {

        ArrayList<Problem> retArray = new ArrayList<>();

        Cursor cursor = new ProblemCursorBuilder(db).retrieveListQuery(problemIds);

        while (cursor.moveToNext()) {
            Problem prob;
            try {
                String problemId = cursor.getString(0);
                String problemTitle = cursor.getString(1);
                String problemDesc = cursor.getString(2);
                Date problemDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(3));

                prob = new Problem(problemId, problemTitle, problemDesc, problemDate);

                for (String recordId : cursor.getString(4).split(",")) {
                    prob.addRecord(recordId);
                }
            }
            catch (ParseException e) {
                Log.d("DBC:retrieveProblemById", "Parse exception", e);
                prob = null;
            }
            retArray.add(prob);
        }
        return retArray;
    }

    @Override
    public void deleteProblem(Problem problem) {
        db.delete("Problems",
                "problemId = ?",
                new String[] {problem.getProblemId()}
                );
    }

}
