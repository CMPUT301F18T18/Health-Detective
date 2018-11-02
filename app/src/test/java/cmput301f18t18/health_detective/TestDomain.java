package cmput301f18t18.health_detective;

import org.junit.Test;

import cmput301f18t18.health_detective.model.domain.CareProvider;
import cmput301f18t18.health_detective.model.domain.Patient;

import static org.junit.Assert.*;

public class TestDomain {
    @Test
    public void TestCareProviderAddPatient() {
        CareProvider Provider = new CareProvider();
        Patient pat = new Patient();
        pat.setUserID("1");

        Provider.AddPatient(pat);

        assertEquals(pat, Provider.GetPatient(0));


    }

}
