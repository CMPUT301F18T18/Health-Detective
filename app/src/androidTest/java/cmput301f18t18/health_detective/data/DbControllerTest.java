package cmput301f18t18.health_detective.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;

import static org.junit.Assert.*;

public class DbControllerTest {

    private DbController dbController = DbController.getInstance();

    @Test
    public void testInsertRetrieveProblem() {
        Problem problem = new Problem("1001", "TestInsert", "Stay", new Date());

        dbController.insertProblem(problem);
        Problem ret = dbController.retrieveProblemById("1001");

        assertEquals(problem, ret);

        dbController.deleteProblem(problem);
    }

    @Test
    public void testDeleteProblem() {
        // Passes because retrieveProblemById is implemented as return null
        // Will fail once retrieveProblemById is implemented
        // Basically to remove we must be first able to check if get works

        Problem problem = new Problem("1002", "TestDelete", "Add", new Date());

        dbController.insertProblem(problem);
        dbController.deleteProblem(problem);

        assertNull(dbController.retrieveProblemById("1002"));
    }

    @Test
    public void testInsertRetrieveRecord() {
        Record record = new Record("2001", "TestInsert", "Stay", null, new Date(), null);

        dbController.insertRecord(record);
        Record ret = dbController.retrieveRecordById("2001");

        assertEquals(record, ret);

        dbController.deleteRecord(record);
    }

    @Test
    public void testDeleteRecord() {
        // Passes because retrieveRecordById is implemented as return null
        // Will fail once retrieveRecordById is implemented
        // Basically to remove we must be first able to check if get works

        Record record = new Record("2002", "TestInsert", "Stay", null, new Date(), null);

        dbController.insertRecord(record);
        dbController.deleteRecord(record);
        assertNull(dbController.retrieveRecordById("2002"));
    }

    @Test
    public void testRetrieveRecordsById() {
        Record record;
        ArrayList<Record> records = new ArrayList<>();
        ArrayList<String> recordIds = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            record = new Record(Integer.toString(2003+i),"","",null, null, null);
            recordIds.add(record.getRecordId());
            records.add(record);
            dbController.insertRecord(record);
        }

        ArrayList<Record> recordsRes = dbController.retrieveRecordsById(recordIds);

        assertEquals(records, recordsRes);

        for (Record rec : records) {
            dbController.deleteRecord(rec);
        }
    }

    @Test
    public void testInsertRetrieveUser() {
        CareProvider careProvider = new CareProvider("care1");
        Patient patient = new Patient("pat1");
        dbController.insertUser(careProvider);
        dbController.insertUser(patient);
        Patient retPatient = dbController.retrievePatientById("pat1");
        CareProvider retCare = dbController.retrieveCareProviderById("care1");
//        assertTrue(careProvider.equals(retCare) && patient.equals(retPatient));
        assertEquals(careProvider, retCare);
        assertEquals(patient, retPatient);

        dbController.deleteUser(careProvider);
        dbController.deleteUser(patient);
    }

    @Test
    public void testDeleteUser() {

        Patient patient = new Patient("pat1");
        CareProvider careProvider = new CareProvider("care1");

        dbController.insertUser(careProvider);
        dbController.deleteUser(careProvider);

        dbController.insertUser(patient);
        dbController.deleteUser(patient);

        Patient retPatient = dbController.retrievePatientById("pat1");
        CareProvider retCareProvider = dbController.retrieveCareProviderById("care1");

        assertNull(retPatient);
        assertNull(retCareProvider);
    }
}

