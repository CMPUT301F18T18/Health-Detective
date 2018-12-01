package cmput301f18t18.health_detective.data.transaction.SQL;

import android.database.Cursor;

import java.util.ArrayList;

public interface QueryBuilder {

    Cursor retrieveQuery(String Id);
    Cursor retrieveListQuery(ArrayList<String> Ids);

}
