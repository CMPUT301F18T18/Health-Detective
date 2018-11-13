package cmput301f18t18.health_detective.domain.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CareProviderTest {
    @Test
    public void TestCareProviderAddPatient() {
        CareProvider Provider = new CareProvider();
        Patient pat = new Patient();
        pat.setUserID("1");

        Provider.AddPatient(pat);

        assertEquals(pat, Provider.GetPatient("1"));
    }
}