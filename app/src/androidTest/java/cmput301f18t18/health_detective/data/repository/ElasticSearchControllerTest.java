package cmput301f18t18.health_detective.data.repository;

import org.junit.Test;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;

import static org.junit.Assert.*;

public class ElasticSearchControllerTest {

    private ElasticSearchController elasticSearchController = ElasticSearchController.getInstance();

    @Test
    public void testInsertRetrieveProblem() {
        //Problem problem = new Problem(1);

        //elasticSearchController.insertProblem(problem);
        Problem ret = elasticSearchController.retrieveProblemById(1);

        //assertEquals(problem, ret);
    }

//    @Test
//    public void testDeleteProblem() {
//        // Passes because retrieveProblemById is implemented as return null
//        // Will fail once retrieveProblemById is implemented
//        // Basically to remove we must be first able to check if get works
//
//        Problem problem = new Problem(2);
//
//        elasticSearchController.insertProblem(problem);
//        elasticSearchController.deleteProblem(problem);
//        assertNull(elasticSearchController.retrieveProblemById(2));
//    }
//
//    @Test
//    public void testInsertRetrieveRecord() {
//        Record record = new Record(1);
//
//        elasticSearchController.insertRecord(record);
//        Record ret = elasticSearchController.retrieveRecordById(1);
//
//        assertEquals(record, ret);
//    }
//
//    @Test
//    public void testDeleteRecord() {
//        // Passes because retrieveRecordById is implemented as return null
//        // Will fail once retrieveRecordById is implemented
//        // Basically to remove we must be first able to check if get works
//
//        Record record = new Record(2);
//
//        elasticSearchController.insertRecord(record);
//        elasticSearchController.deleteRecord(record);
//        assertNull(elasticSearchController.retrieveRecordById(2));
//    }
//
//    @Test
//    public void testInsertRetrieveUser() {
//        CareProvider careProvider = new CareProvider();
//        careProvider.setUserID("1");
//
//        elasticSearchController.insertUser(careProvider);
//        User ret = elasticSearchController.retrieveUserById("1");
//
//        assertEquals(careProvider, ret);
//    }
//
//
//    @Test
//    public void testDeleteUser() {
//        // Passes because retrieveRecordById is implemented as return null
//        // Will fail once retrieveRecordById is implemented
//        // Basically to remove we must be first able to check if get works
//
//        Patient patient = new Patient();
//        patient.setUserID("2");
//
//        elasticSearchController.insertUser(patient);
//        elasticSearchController.deleteUser(patient);
//        assertNull(elasticSearchController.retrieveUserById("2"));
//    }
}

