package cmput301f18t18.health_detective.data.repository;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;

import static org.junit.Assert.*;

public class ElasticSearchControllerTest {

    private ElasticSearchController elasticSearchController = ElasticSearchController.getInstance();

    @Test
    public void testInsertRetrieveProblem() {
        Problem problem = new Problem("1001", "TestInsert", "Stay", new Date());

        elasticSearchController.insertProblem(problem);
        Problem ret = elasticSearchController.retrieveProblemById("1001");

        assertEquals(problem, ret);

        elasticSearchController.deleteProblem(problem);
    }

    @Test
    public void testDeleteProblem() {
        // Passes because retrieveProblemById is implemented as return null
        // Will fail once retrieveProblemById is implemented
        // Basically to remove we must be first able to check if get works

        Problem problem = new Problem("1002", "TestDelete", "Add", new Date());

        elasticSearchController.insertProblem(problem);
        elasticSearchController.deleteProblem(problem);

        assertNull(elasticSearchController.retrieveProblemById("1002"));
    }

    @Test
    public void testInsertRetrieveRecord() {
        Record record = new Record("2001", "TestInsert", "Stay", new Date());

        elasticSearchController.insertRecord(record);
        Record ret = elasticSearchController.retrieveRecordById("2001");

        assertEquals(record, ret);

        elasticSearchController.deleteRecord(record);
    }

    @Test
    public void testDeleteRecord() {
        // Passes because retrieveRecordById is implemented as return null
        // Will fail once retrieveRecordById is implemented
        // Basically to remove we must be first able to check if get works

        Record record = new Record("2002", "TestInsert", "Stay", new Date());

        elasticSearchController.insertRecord(record);
        elasticSearchController.deleteRecord(record);
        assertNull(elasticSearchController.retrieveRecordById("2002"));
    }

    @Test
    public void testRetrieveRecordsById() {
        Record record;
        ArrayList<Record> records = new ArrayList<>();
        ArrayList<String> recordIds = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            record = new Record(Integer.toString(2003+i),"","",null);
            recordIds.add(record.getRecordId());
            records.add(record);
            elasticSearchController.insertRecord(record);
        }

        ArrayList<Record> recordsRes = elasticSearchController.retrieveRecordsById(recordIds);

        assertEquals(records, recordsRes);

        for (Record rec : records) {
            elasticSearchController.deleteRecord(rec);
        }
    }

    @Test
    public void testInsertRetrieveUser() {
        CareProvider careProvider = new CareProvider("care1");
        Patient patient = new Patient("pat1");
        elasticSearchController.insertUser(careProvider);
        elasticSearchController.insertUser(patient);
        Patient retPatient = elasticSearchController.retrievePatientById("pat1");
        CareProvider retCare = elasticSearchController.retrieveCareProviderById("care1");

        assertTrue(careProvider.equals(retCare) && patient.equals(retPatient));

        elasticSearchController.deleteUser(careProvider);
        elasticSearchController.deleteUser(patient);
    }

    @Test
    public void testDeleteUser() {
        // Passes because retrieveRecordById is implemented as return null
        // Will fail once retrieveRecordById is implemented
        // Basically to remove we must be first able to check if get works

        Patient patient = new Patient("pat1");
        CareProvider careProvider = new CareProvider("care1");

        elasticSearchController.insertUser(careProvider);
        elasticSearchController.deleteUser(careProvider);

        elasticSearchController.insertUser(patient);
        elasticSearchController.deleteUser(patient);
        assertTrue((elasticSearchController.retrievePatientById("pat1") == null)
                && (elasticSearchController.retrieveCareProviderById("care1") == null));
    }
}

