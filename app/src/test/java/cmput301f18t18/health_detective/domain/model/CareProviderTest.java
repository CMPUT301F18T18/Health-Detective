//package cmput301f18t18.health_detective.domain.model;
//
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//import static org.junit.Assert.*;
//
//public class CareProviderTest {
//
//    @Test
//    public void addPatient() {
//        CareProvider careProvider = new CareProvider(
//                "careProvider"
//        );
//
//        Patient patientToAdd = new Patient(
//                "patient1"
//        );
//
//        careProvider.addPatient(patientToAdd);
//
//        ArrayList<String> expectedPatientIds = new ArrayList<>();
//        ArrayList<String> patientIds = careProvider.getPatientIds();
//
//        expectedPatientIds.add("patient1");
//
//        assertEquals(expectedPatientIds, patientIds);
//    }
//
//    @Test
//    public void addPatient_Null() {
//        CareProvider careProvider = new CareProvider(
//                "careProvider"
//        );
//
//        careProvider.addPatient(null);
//
//        ArrayList<String> expectedPatientIds = new ArrayList<>();
//        ArrayList<String> patientIds = careProvider.getPatientIds();
//
//        assertEquals(expectedPatientIds, patientIds);
//    }
//
//    @Test
//    public void removePatientWithObj() {
//        CareProvider careProvider = new CareProvider(
//                "careProvider"
//        );
//
//        Patient patientToAdd = new Patient(
//                "patient1"
//        );
//
//        careProvider.addPatient(patientToAdd);
//        careProvider.removePatient(patientToAdd);
//
//        ArrayList<String> expectedPatientIds = new ArrayList<>();
//        ArrayList<String> patientIds = careProvider.getPatientIds();
//
//        assertEquals(expectedPatientIds, patientIds);
//    }
//
//    @Test
//    public void removePatientNull() {
//        CareProvider careProvider = new CareProvider(
//                "careProvider"
//        );
//
//        Patient patientToAdd = new Patient(
//                "patient1"
//        );
//
//        careProvider.addPatient(patientToAdd);
//        careProvider.removePatient(null);
//
//        ArrayList<String> expectedPatientIds = new ArrayList<>();
//        ArrayList<String> patientIds = careProvider.getPatientIds();
//
//        expectedPatientIds.add("patient1");
//
//        assertEquals(expectedPatientIds, patientIds);
//    }
//
//    @Test
//    public void isPatientsEmpty() {
//        CareProvider careProvider = new CareProvider(
//                "careprovider",
//                "blank",
//                "blank"
//        );
//
//        assertTrue(careProvider.isPatientsEmpty());
//    }
//
//    @Test
//    public void isPatientsEmpty_AddingAPatient() {
//        CareProvider careProvider = new CareProvider();
//
//        Patient patient = new Patient(
//                "patient"
//        );
//
//        careProvider.addPatient(patient);
//
//        assertFalse(careProvider.isPatientsEmpty());
//    }
//
//    @Test
//    public void isPatientsEmpty_AddingNull() {
//        CareProvider careProvider = new CareProvider();
//
//        careProvider.addPatient(null);
//
//        assertTrue(careProvider.isPatientsEmpty());
//    }
//
//    @Test
//    public void isPatientsEmpty_AddingPatientWithNullId() {
//        CareProvider careProvider = new CareProvider();
//        Patient patient = new Patient();
//
//        assertEquals(null, patient.getUserId());
//
//        careProvider.addPatient(patient);
//
//        assertTrue(careProvider.isPatientsEmpty());
//    }
//
//    @Test
//    public void isPatientsEmpty_AddingPatientWithEmptyId() {
//        CareProvider careProvider = new CareProvider();
//        Patient patient = new Patient("");
//
//        assertEquals("", patient.getUserId());
//
//        careProvider.addPatient(patient);
//
//        assertTrue(careProvider.isPatientsEmpty());
//    }
//
//    @Test
//    public void isPatientsEmpty_AfterRemoving() {
//        CareProvider careProvider = new CareProvider();
//        Patient patient1 = new Patient("test");
//        Patient patient2 = new Patient("test2");
//
//        careProvider.addPatient(patient1);
//        careProvider.addPatient(patient2);
//        careProvider.removePatient(patient1);
//        careProvider.removePatient(patient2);
//
//        assertTrue(careProvider.isPatientsEmpty());
//    }
//
//    @Test
//    public void getProblemIds() {
//        Patient patient = new Patient();
//
//        patient.addProblem(1);
//        patient.addProblem(2000);
//        patient.addProblem(100);
//
//        ArrayList<Integer> patientsProblemIds = patient.getProblemIds();
//
//        assertTrue(patientsProblemIds.contains(1));
//        assertTrue(patientsProblemIds.contains(2000));
//        assertTrue(patientsProblemIds.contains(100));
//    }
//
//    @Test
//    public void hasPatientObj() {
//        CareProvider careProvider = new CareProvider();
//
//        Patient patient = new Patient(
//                "patient"
//        );
//
//        careProvider.addPatient(patient);
//
//        assertTrue(careProvider.hasPatient(patient));
//    }
//
//    @Test
//    public void hasPatientPatientId() {
//        CareProvider careProvider = new CareProvider();
//
//        Patient patient = new Patient(
//                "patient"
//        );
//
//        careProvider.addPatient(patient);
//
//        assertTrue(careProvider.hasPatient("patient"));
//    }
//
//    @Test
//    public void hasPatientObjNull() {
//        CareProvider careProvider = new CareProvider();
//
//        Patient patient = new Patient(
//                "patient"
//        );
//
//        careProvider.addPatient(patient);
//
//        assertFalse(careProvider.hasPatient((Patient) null));
//    }
//
//    @Test
//    public void hasPatientPatientIdNull() {
//        CareProvider careProvider = new CareProvider();
//
//        Patient patient = new Patient(
//                "patient"
//        );
//
//        careProvider.addPatient(patient);
//
//        assertFalse(careProvider.hasPatient((String) null));
//    }
//
//    @Test
//    public void DoesNotHavePatientPatientId() {
//        CareProvider careProvider = new CareProvider();
//
//        Patient patient = new Patient(
//                "patient"
//        );
//
//        careProvider.addPatient(patient);
//
//        assertFalse(careProvider.hasPatient("random"));
//    }
//
//    @Test
//    public void DoesNotHavePatientPatientObj() {
//        CareProvider careProvider = new CareProvider();
//
//        Patient patient = new Patient(
//                "patient"
//        );
//
//        Patient patient1 = new Patient("username");
//
//        careProvider.addPatient(patient);
//
//        assertFalse(careProvider.hasPatient(patient1));
//    }
//}