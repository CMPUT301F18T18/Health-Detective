package cmput301f18t18.health_detective.model.domain;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SearchTest {
    //Search empty, search single, search multiple, search mixed
    @Test
    public void testSearchKeywordEmpty() {
        Search search = new Search();

        assertTrue(search.searchKeywords(new ArrayList<Searchable>(), null).isEmpty());
    }

    @Test
    public void testSearchBodyPartEmpty() {
        Search search = new Search();

        assertTrue(search.searchBodyPart(new ArrayList<Searchable>(), null).isEmpty());
    }

    @Test
    public void testSearchGeolocationEmpty() {
        Search search = new Search();

        assertTrue(search.searchGeolocation(new ArrayList<Searchable>(), null).isEmpty());
    }

    @Test
    public void testSearchKeywordOne() {
        Search search = new Search();

        ArrayList<Problem> arr = new ArrayList<>();
        Problem problem = new Problem();
        arr.add(problem);

        ArrayList<Problem> ret = search.searchKeywords(arr, null);
        assertEquals(arr, ret);
    }

    @Test
    public void testSearchBodyPartOne() {
        Search search = new Search();

        ArrayList<Problem> arr = new ArrayList<>();
        Problem problem = new Problem();
        arr.add(problem);

        ArrayList<Problem> ret = search.searchBodyPart(arr, null);
        assertEquals(arr, ret);
    }

    @Test
    public void testSearchGeolocation() {
        Search search = new Search();

        ArrayList<Problem> arr = new ArrayList<>();
        Problem problem = new Problem();
        arr.add(problem);

        ArrayList<Problem> ret = search.searchGeolocation(arr, null);
        assertEquals(arr, ret);
    }

}
