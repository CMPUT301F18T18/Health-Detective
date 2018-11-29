package cmput301f18t18.health_detective.domain.model.context.tree;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.RecordContext;

import static org.junit.Assert.*;

public class ContextTreeParserTest {

    ContextTree tree;

    @Before
    public void init() {
        tree = new ContextTree();
        Class cl = null;
        try {
            cl = Class.forName("cmput301f18t18.health_detective.domain.interactors.impl.AddAssignedPatientImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        tree.push(new RecordContext(cl, new Record()));
        tree.push(new CareProviderContext(cl, new CareProvider()));
    }

    @Test
    public void getLoggedInUser() {
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        User loggedInUser = treeParser.getLoggedInUser();
    }

    @Test
    public void getCurrentUserContext() {
    }

    @Test
    public void getCurrentProblemContext() {
    }

    @Test
    public void getCurrentRecordContext() {
    }

    @Test
    public void getCurrentBodyLocation() {
    }

    @Test
    public void getCurrentBodyPhoto() {
    }
}