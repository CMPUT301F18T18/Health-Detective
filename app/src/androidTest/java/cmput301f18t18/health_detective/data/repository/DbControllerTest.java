package cmput301f18t18.health_detective.data.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.model.Problem;

import static org.junit.Assert.assertEquals;

public class DbControllerTest {
    private DbController dbController = DbController.getInstance();

    private Context appContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setup() {
        dbController.init(appContext);
    }

    @Test
    public void testInsertRetrieveProblem() {
        Problem problem = new Problem(1001, "TestInsert", "Stay", new Date());
        dbController.insertProblem(problem);
        Problem ret = dbController.retrieveProblemById(1001);

        assertEquals(problem, ret);

//        dbController.deleteProblem(problem);
    }

}
