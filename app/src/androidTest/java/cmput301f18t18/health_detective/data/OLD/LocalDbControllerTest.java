/*package cmput301f18t18.health_detective.data.OLD;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;

import static org.junit.Assert.assertEquals;

public class LocalDbControllerTest {
    private LocalDbController localDbController = LocalDbController.getInstance();

    private Context appContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setup() {
        localDbController.init(appContext);
    }

    @Test
    public void testInsertRetrieveProblem() {
        Problem problem = new Problem("1001", "TestInsert", "Stay", new Date());
        problem.addRecord(new Record("1", "a", "b", new Date()));
        problem.addRecord(new Record("2", "c", "d", new Date()));
        problem.addRecord(new Record("3", "e", "f", new Date()));
        localDbController.insertProblem(problem);
        Problem ret = localDbController.retrieveProblemById("1001");

        assertEquals(problem, ret);

//        localDbController.deleteProblem(problem);
    }

}*/
