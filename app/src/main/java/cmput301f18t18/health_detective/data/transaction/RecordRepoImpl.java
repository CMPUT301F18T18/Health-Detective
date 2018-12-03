package cmput301f18t18.health_detective.data.transaction;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.data.transaction.base.AbstractRepo;
import cmput301f18t18.health_detective.domain.model.Record;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class RecordRepoImpl extends AbstractRepo {

    private Record record;
    private String recordId;

    public RecordRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, Record record) {
        super(client, index, db);
        this.record = record;
        this.recordId = record.getRecordId();
    }

    public RecordRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, String id) {
        super(client, index, db);
        this.recordId = id;
    }

    /**
     * Returns a records's unique _ID out of elastic search based on a record ID.
     * Used in deletion.
     *
     * @return          The record's _ID from elasticsearch
     */
    private String getRecordElasticSearchId() {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"recordId\": \"" + recordId + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getRecordElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex(elasticIndex)
                .addType("Record")
                .build();
        try {
            SearchResult result = client.execute(search);
            List<SearchResult.Hit<Record, Void>> records = result.getHits(Record.class);

            Log.d("ESC:getRecordElasticSearchId", "Result succeeded");

            if (records.size() == 0) {
                Log.d("ESC:getRecordElasticSearchId", "No results found");
                return null;
            }
            for (SearchResult.Hit<Record, Void> hit : records) {
                Log.d("ESC:getRecordElasticSearchId", hit.id);
            }
            return records.get(0).id;
        } catch (IOException e) {
            Log.d("ESC:getRecordElasticSearchId", "IOEception", e);
        }

        return null;
    }

    /**
     * Function to push a record into elastic search.
     *
     */
    @Override
    public void insert() {
        Index index = new Index.Builder(record)
                .index(elasticIndex)
                .type(record.getClass().getSimpleName())
                .refresh(true)
                .build();
        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                Log.d("ESC:insertRecord", "Record inserted");
                Log.d("ESC:insertRecord", result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:insertRecord", "IOException", e);
        }
    }

    /**
     * Used to update the old info for a record in elasticsearch with new info.
     * Currently done by deleting then re-inserting record.
     *
     */
    @Override
    public void update() {
        delete();
        insert();
    }

    /**
     * Used to return a record from elasticsearch based on its recordID
     *
     * @return          The record associated with recordID
     */
    public Record retrieve() {
        if (recordId == null)
            return null;

        String elasticSearchId = getRecordElasticSearchId();
        Get get = new Get.Builder(elasticIndex, elasticSearchId)
                .type("Record")
                .build();
        try {
            JestResult result = client.execute(get);
            if (result.isSucceeded()) {
                return result.getSourceAsObject(Record.class);
            }
            else {
                Log.d("ESC:retrieveRecordById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveRecordById", "IOException", e);
        }
        return null;
    }

    /**
     * Gets a list of records from a list of record IDs
     *
     * @param recordID The list of record ids to find
     * @return          A list of associated record ids
     */
    public ArrayList<Record> retrieveRecordsById(ArrayList<String> recordID) {
        ArrayList<Record> records = new ArrayList<>();

        for (String id : recordID) {
            this.recordId = id;
            Record record = retrieve();

            if (record == null)
                continue;

            records.add(record);
        }

        return records;
    }

    /**
     * Used to remove a record from elasticsearch
     *
     */
    @Override
    public void delete() {
        String elasticSearchId = getRecordElasticSearchId();
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index(elasticIndex)
                .type(record.getClass().getSimpleName())
                .build();
        try {
            DocumentResult result = client.execute(delete);
            if (result.isSucceeded()) {
                Log.d("ESC:deleteRecord", "Deleted" + result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:deleteRecord", "IOException", e);
        }
    }
}
