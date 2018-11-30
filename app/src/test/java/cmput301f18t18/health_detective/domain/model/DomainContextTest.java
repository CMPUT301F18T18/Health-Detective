package cmput301f18t18.health_detective.domain.model;

import org.junit.Test;

import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.util.Id;

public class DomainContextTest {

    @Test
    public void genUniqueId() {

        DomainContext context = DomainContext.getInstance();


        System.out.println("Random key:\n" + Id.genUniqueId(context.getSecureRandom()));
    }
}