package cmput301f18t18.health_detective.data.transaction.factory;

import android.database.sqlite.SQLiteDatabase;

import com.searchly.jestdroid.JestDroidClient;

import cmput301f18t18.health_detective.data.transaction.PhotoRepoImpl;
import cmput301f18t18.health_detective.data.transaction.base.AbstractRepo;
import cmput301f18t18.health_detective.data.transaction.ProblemRepoImpl;
import cmput301f18t18.health_detective.data.transaction.RecordRepoImpl;
import cmput301f18t18.health_detective.data.transaction.UserRepoImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;

public abstract class RepoFactory {

    private RepoFactory() { }
    public static AbstractRepo build(JestDroidClient client, String index, SQLiteDatabase db, Boolean online, Object o) {
        if (o instanceof Problem)
            return new ProblemRepoImpl(client, index, db, online, (Problem) o);
        if (o instanceof Record)
            return new RecordRepoImpl(client, index, db, online, (Record) o);
        if (o instanceof User)
            return new UserRepoImpl(client, index, db, online, (User) o);
        if (o instanceof DomainImage)
            return new PhotoRepoImpl(client, index, db, online, (DomainImage) o);
        return null;
    }

    public static AbstractRepo build(JestDroidClient client, String index, SQLiteDatabase db, Boolean online, Class clazz, String id) {
        if (clazz == Problem.class)
            return new ProblemRepoImpl(client, index, db, online, id);
        if (clazz == Record.class)
            return new RecordRepoImpl(client, index, db, online, id);
        if (clazz == Patient.class || clazz == CareProvider.class || clazz == User.class)
            return new UserRepoImpl(client, index, db, online, id);
        if (clazz == DomainImage.class)
            return new PhotoRepoImpl(client, index, db, online, id);
        return null;
    }

}
