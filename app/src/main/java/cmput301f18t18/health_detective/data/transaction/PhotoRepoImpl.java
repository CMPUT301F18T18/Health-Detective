package cmput301f18t18.health_detective.data.transaction;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.data.transaction.base.AbstractRepo;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class PhotoRepoImpl extends AbstractRepo {

    private DomainImage image;
    private String imageId;

    public PhotoRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, DomainImage image) {
        super(client, index, db);
        this.image = image;
        this.imageId = image.getImageId();
    }

    public PhotoRepoImpl(JestDroidClient client, String index, SQLiteDatabase db, String id) {
        super(client, index, db);
        this.imageId = id;
    }


    private String getImageElasticSearchId() {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"imageId\": \"" + imageId + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Log.d("ESC:getImageElasticSearchId", query);
        Search search = new Search.Builder(query)
                .addIndex(elasticIndex)
                .addType("DomainImage")
                .build();
        try {
            SearchResult result = getClient().execute(search);
            List<SearchResult.Hit<DomainImage, Void>> images = result.getHits(DomainImage.class);

            Log.d("ESC:getImageElasticSearchId", "Result succeeded");

            if (images.size() == 0) {
                Log.d("ESC:getImageElasticSearchId", "No results found");
                return null;
            }
            for (SearchResult.Hit<DomainImage, Void> hit : images) {
                Log.d("ESC:getImageElasticSearchId", hit.id);
            }
            return images.get(0).id;
        } catch (IOException e) {
            Log.d("ESC:getImageElasticSearchId", "IOException", e);
        }
        return null;
    }
    
    public void insert() {
        if (image == null)
            return;

        Index index = new Index.Builder(image)
                .index(elasticIndex)
                .type(image.getClass().getSimpleName())
                .refresh(true)
                .build();
        try {
            DocumentResult result = getClient().execute(index);
            if (result.isSucceeded()) {
                Log.d("ESC:insertImage", "Image inserted");
                Log.d("ESC:insertImage", result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:insertImage", "IOException", e);
        }
    }

    @Override
    public void update() {  
        // Not needed functionality
    }

    @Override
    public void delete() {
        String elasticSearchId = getImageElasticSearchId();
        if (elasticSearchId == null)
            return;
        Delete delete = new Delete.Builder(elasticSearchId)
                .index(elasticIndex)
                .type(image.getClass().getSimpleName())
                .build();
        try {
            DocumentResult result = getClient().execute(delete);
            if (result.isSucceeded()) {
                Log.d("ESC:deleteImage", "Deleted" + result.getId());
            }
        } catch (IOException e) {
            Log.d("ESC:deleteImage", "IOException", e);
        }
    }

    public DomainImage retrieve() {
        String elasticSearchId = getImageElasticSearchId();
        Get get = new Get.Builder(elasticIndex, elasticSearchId)
                .type("DomainImage")
                .build();
        try {
            JestResult result = getClient().execute(get);
            if (result.isSucceeded()) {
                return result.getSourceAsObject(DomainImage.class);
            }
            else {
                Log.d("ESC:retrieveImageById", "result not succeeded");
            }
        } catch (IOException e) {
            Log.d("ESC:retrieveImageById", "IOException", e);
        }
        return null;
    }
    

    public ArrayList<DomainImage> retrievePhotosById(ArrayList<String> ids) {
        ArrayList<DomainImage> images = new ArrayList<>();

        for (String id : ids) {
            this.imageId = id;

            DomainImage image = retrieve();

            if (image != null)
                images.add(image);
        }

        return images;
    }
}
