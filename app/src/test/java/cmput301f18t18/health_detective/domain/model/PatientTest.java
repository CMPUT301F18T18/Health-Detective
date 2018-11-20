package cmput301f18t18.health_detective.domain.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class PatientTest {

    @Test
    public void addProblemWithProblemObj() {
        Patient patientToAddToo = new Patient(
                "patient1"
        );

        Problem problemToAdd = new Problem(
                1,
                "Title",
                "Description",
                new Date()
        );

        patientToAddToo.addProblem(problemToAdd);

        ArrayList<Integer> expectedProblemIds = new ArrayList<>();
        ArrayList<Integer> patientsProblemIds = patientToAddToo.getProblemIds();

        expectedProblemIds.add(1);

        assertEquals(expectedProblemIds, patientsProblemIds);
    }

    @Test
    public void addProblemAddProblemById() {
        Patient patientToAddToo = new Patient(
                "patient"
        );

        patientToAddToo.addProblem(1);

        ArrayList<Integer> expectedProblemIds = new ArrayList<>();
        ArrayList<Integer> patientsProblemIds = patientToAddToo.getProblemIds();

        expectedProblemIds.add(1);

        assertEquals(expectedProblemIds, patientsProblemIds);
    }

    @Test
    public void removeProblem() {
        Patient patientToRemoveFrom = new Patient(
                "patient"
        );

        Problem problemToRemove = new Problem(
                1,
                "Title",
                "Description",
                new Date()
        );

        patientToRemoveFrom.addProblem(problemToRemove);


        patientToRemoveFrom.removeProblem(problemToRemove);

        ArrayList<Integer> expectedProblemIds = new ArrayList<>();
        ArrayList<Integer> patientsProblemIds = patientToRemoveFrom.getProblemIds();


        assertEquals(expectedProblemIds, patientsProblemIds);

    }

    @Test
    public void removeProblem1() {
        Patient patientToRemoveFrom = new Patient(
                "patient"
        );

        patientToRemoveFrom.addProblem(1);
        patientToRemoveFrom.removeProblem(1);

        ArrayList<Integer> expectedProblemIds = new ArrayList<>();
        ArrayList<Integer> patientsProblemIds = patientToRemoveFrom.getProblemIds();


        assertEquals(expectedProblemIds, patientsProblemIds);
    }

    @Test
    public void isProblemsEmpty() {
        Patient patient = new Patient(
                "patient"
        );

        assertTrue(patient.isProblemsEmpty());
    }

    @Test
    public void isProblemsEmpty_AddingAProblem() {
        Patient patient = new Patient(
                "patient"
        );

        patient.addProblem(1);

        assertFalse(patient.isProblemsEmpty());
    }

    @Test
    public void isProblemsEmpty_AfterRemoving() {
        Patient patient = new Patient(
                "patient",
                "phonenumber",
                "email"
        );

        patient.addProblem(1);
        patient.addProblem(2);
        patient.removeProblem(1);
        patient.removeProblem(2);

        assertTrue(patient.isProblemsEmpty());
    }

    @Test
    public void getProblemIds() {
        Patient patient = new Patient();

        patient.addProblem(1);
        patient.addProblem(2000);
        patient.addProblem(100);

        ArrayList<Integer> patientsProblemIds = patient.getProblemIds();

        assertTrue(patientsProblemIds.contains(1));
        assertTrue(patientsProblemIds.contains(2000));
        assertTrue(patientsProblemIds.contains(100));
    }
}