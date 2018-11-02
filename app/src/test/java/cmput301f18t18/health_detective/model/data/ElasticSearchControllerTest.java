package cmput301f18t18.health_detective.model.data;

import org.junit.Test;

import cmput301f18t18.health_detective.model.domain.CareProvider;
import cmput301f18t18.health_detective.model.domain.Problem;
import cmput301f18t18.health_detective.model.domain.Record;
import cmput301f18t18.health_detective.model.domain.User;

import static org.junit.Assert.*;

public class ElasticSearchControllerTest {

    private ElasticSearchController elasticSearchController = ElasticSearchController.getInstance();

    @Test
    public void testSetGetUser() {
        CareProvider careProvider = new CareProvider();
        careProvider.setUserID("1");

        elasticSearchController.saveUser(careProvider);
        User ret = elasticSearchController.getUser("1");

        assertEquals(careProvider, ret);
    }

    @Test
    public void testSaveGetProblem() {
        Problem problem = new Problem(1);

        elasticSearchController.saveProblem(problem);
        Problem ret = elasticSearchController.getProblem(1);

        assertEquals(problem, ret);
    }

    @Test
    public void testSaveGetRecord() {
        Record record = new Record(1);

        elasticSearchController.saveRecord(record);
        Record ret = elasticSearchController.getRecord(1);

        assertEquals(record, ret);
    }

    @Test
    public void testRemoveProblem() {
        // Passes because getProblem is implemented as return null
        // Will fail once getProblem is implemented but removeProblem isn't
        // Basically to remove we must be first able to check if get works

        Problem problem = new Problem(2);

        elasticSearchController.saveProblem(problem);
        elasticSearchController.removeProblem(2);
        assertNull(elasticSearchController.getProblem(2));
    }

    @Test
    public void testRemoveRecord() {
        // Passes because getRecord is implemented as return null
        // Will fail once getRecord is implemented but removeRecord isn't
        // Basically to remove we must be first able to check if get works

        Record record = new Record(2);

        elasticSearchController.saveRecord(record);
        elasticSearchController.removeRecord(2);
        assertNull(elasticSearchController.getRecord(2));
    }
}