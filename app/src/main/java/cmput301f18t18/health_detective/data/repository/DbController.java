package cmput301f18t18.health_detective.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    @Override
    public void insertProblem(Problem problem) {
        ContentValues values = new ContentValues();
        values.put("problemId", problem.getProblemID());
        values.put("title", problem.getTitle());
        values.put("description", problem.getDescription());
        values.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problem.getStartDate()));

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : problem.getRecordIds()) {
            stringBuilder.append(id.toString()).append(",");
        }

        String recordIds = stringBuilder.toString();
        if (stringBuilder.length() > 2) { // Trim trailing ","
            recordIds = recordIds.substring(0, recordIds.length()-1);
        }

        values.put("recordIds", recordIds);

        db.insert("Problems", null, values);
    }

    @Override
    public void updateProblem(Problem problem) { }

    @Override
    public Problem retrieveProblemById(Integer problemID) {
        Cursor cursor = new ProblemQueryBuilder(db).retrieveQuery(Integer.toString(problemID));
        cursor.moveToNext();
        Problem ret;
        try {
            Integer problemId = Integer.parseInt(cursor.getString(0));
            String problemTitle = cursor.getString(1);
            String problemDesc = cursor.getString(2);
            Date problemDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(3));

            ret = new Problem(problemId, problemTitle, problemDesc, problemDate);

            for (String recordId : cursor.getString(4).split(",")) {
                ret.addRecord(Integer.parseInt(recordId));
            }
        }
        catch (ParseException e) {
            Log.d("DBC:retrieveProblemById", "Parse exception", e);
            ret = null;
        }
        return ret;
    }

    @Override
    public ArrayList<Problem> retrieveProblemsById(ArrayList<Integer> problemIds) {

        // TODO: REMOVE ONCE IDS CHANGE TO STRING
        ArrayList<String> problemIdStrings = new ArrayList<>();
        for (Integer i: problemIds) {
            problemIdStrings.add(i.toString());
        }

        ArrayList<Problem> retArray = new ArrayList<>();

        Cursor cursor = new ProblemQueryBuilder(db).retrieveListQuery(problemIdStrings);

        while (cursor.moveToNext()) {
            Problem prob;
            try {
                Integer problemId = Integer.parseInt(cursor.getString(0));
                String problemTitle = cursor.getString(1);
                String problemDesc = cursor.getString(2);
                Date problemDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(3));

                prob = new Problem(problemId, problemTitle, problemDesc, problemDate);

                for (String recordId : cursor.getString(4).split(",")) {
                    prob.addRecord(Integer.parseInt(recordId));
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
    public void deleteProblem(Problem problem) {}


}
