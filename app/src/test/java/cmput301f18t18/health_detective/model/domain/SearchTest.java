package cmput301f18t18.health_detective.model.domain;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SearchTest {

    private Search search = new Search();

    public ArrayList<Record> CreateRecordArray(int count) {

        ArrayList<Record> records = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            records.add(new Record(i));
        }

        return records;
    }

    public ArrayList<Problem> CreateProblemArray(int count) {

        ArrayList<Problem> problems = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            problems.add(new Problem(i));
        }

        return problems;
    }
    //Search empty, search single, search multiple, search mixed
    @Test
    public void testSearchKeywordEmpty() {
        ArrayList<Problem> problems = new ArrayList<>();
        assertTrue(search.searchKeywords(problems, null).isEmpty());
    }

    @Test
    public void testSearchBodyPartEmpty() {
        ArrayList<Problem> problems = new ArrayList<>();
        assertTrue(search.searchBodyPart(problems, null).isEmpty());
    }

    @Test
    public void testSearchGeolocationEmpty() {
        ArrayList<Problem> problems = new ArrayList<>();
        assertTrue(search.searchGeolocation(problems, null).isEmpty());
    }

    @Test
    public void testSearchKeywordOne() {
        ArrayList<Problem> problems = new ArrayList<>();
        Problem problem = new Problem();
        problems.add(problem);

        ArrayList<Problem> ret = search.searchKeywords(problems, null);
        assertEquals(problems, ret);
    }

    @Test
    public void testSearchBodyPartOne() {
        ArrayList<Problem> problems = new ArrayList<>();
        Problem problem = new Problem();
        problems.add(problem);

        ArrayList<Problem> ret = search.searchBodyPart(problems, null);
        assertEquals(problems, ret);
    }

    @Test
    public void testSearchGeolocationOne() {
        ArrayList<Problem> problems = new ArrayList<>();
        Problem problem = new Problem();
        problems.add(problem);

        ArrayList<Problem> ret = search.searchGeolocation(problems, null);
        assertEquals(problems, ret);
    }

    @Test
    public void testSearchKeywordMultipleNoCriteria() {
        ArrayList<Problem> problems = CreateProblemArray(3);

        ArrayList<Problem> ret = search.searchKeywords(problems, null);

        assertEquals(problems, ret);
    }

    @Test
    public void testSearchBodyPartMultipleNoCriteria() {
        ArrayList<Problem> problems = CreateProblemArray(3);

        ArrayList<Problem> ret = search.searchBodyPart(problems, null);
        assertEquals(problems, ret);
    }

    @Test
    public void testSearchGeolocationMultipleNoCriteria() {
        ArrayList<Problem> problems = CreateProblemArray(3);

        ArrayList<Problem> ret = search.searchGeolocation(problems, null);
        assertEquals(problems, ret);
    }

    @Test
    public void testSearchKeywordCriteria() {
        ArrayList<Problem> problems = CreateProblemArray(3);

        problems.get(0).setDescription("test abc");
        problems.get(1).setDescription("abc test");
        problems.get(2).setDescription("abc");

        ArrayList<String> crit = new ArrayList<>();

        crit.add("test");

        ArrayList<Problem> ret = search.searchKeywords(problems, crit);

        assertTrue(ret.contains(problems.get(0)) && ret.contains(problems.get(1)));
    }

    @Test
    public void testSearchBodyPartCriteria() {
        // Problems recursively search records for body locations and geolocations
        ArrayList<Problem> problems = CreateProblemArray(3);

        Record record = new Record(1);
        BodyLocation loc = new BodyLocation();

        loc.setLocation(BodyPart.LeftLeg);
        record.addBodyLocation(loc);

        problems.get(1).addRecord(record);

        ArrayList<Problem> ret = search.searchBodyPart(problems, BodyPart.LeftLeg);

        assertTrue(ret.size() == 1 && ret.contains(problems.get(1)));
    }

    @Test
    public void testSearchGeolocationCriteria() {
        // Original ordering: FURTHEST, CLOSEST, NONE
        ArrayList<Record> records = CreateRecordArray(3);

        Record record = records.get(0);
        Geolocation geoloc = new Geolocation(0, 4);

        record.setLocation(geoloc);

        record = records.get(1);
        geoloc = new Geolocation(0, 2);

        record.setLocation(geoloc);

        ArrayList<Record> ret = search.searchGeolocation(records, new Geolocation(0,0));

        // Sort problems and compare to ret, should be same

        ArrayList<Record> sorted = new ArrayList<>();
        sorted.add(records.get(1));
        sorted.add(records.get(0));

        assertEquals(sorted, ret);
    }
}
