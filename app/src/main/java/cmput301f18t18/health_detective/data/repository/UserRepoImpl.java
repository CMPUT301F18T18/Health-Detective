package cmput301f18t18.health_detective.data.repository;

import android.database.sqlite.SQLiteDatabase;

import com.searchly.jestdroid.JestDroidClient;

import cmput301f18t18.health_detective.domain.model.User;

public class UserRepoImpl extends AbstractRepo {


    private User user;
    private String userId;

    UserRepoImpl(JestDroidClient client, SQLiteDatabase db, User user) {
        super(client, db);
        this.user = user;
        this.userId = user.getUserId();
    }

    UserRepoImpl(JestDroidClient client, SQLiteDatabase db, String id) {
        super(client, db);
        this.userId = id;
    }
    
    
    @Override
    void insert() {
        
    }

    @Override
    void update() {

    }

    @Override
    void delete() {

    }
}
