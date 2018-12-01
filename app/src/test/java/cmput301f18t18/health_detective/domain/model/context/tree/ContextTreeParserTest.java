package cmput301f18t18.health_detective.domain.model.context.tree;

import org.junit.Before;
import org.junit.Test;

import cmput301f18t18.health_detective.domain.interactors.AddAssignedPatient;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.domain.model.context.base.DomainContext;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.RecordContext;

import static org.junit.Assert.*;

public class ContextTreeParserTest {

    ContextTree tree;

    @Before
    public void init() {
        tree = new ContextTree();

        tree.push(ContextTreeComponentFactory.getContextComponent(new Record("r1", "")));
        tree.push(ContextTreeComponentFactory.getContextComponent(new Patient("patient")));
        tree.push(ContextTreeComponentFactory.getContextComponent(new Problem("p1", "")));
        tree.push(ContextTreeComponentFactory.getContextComponent(new Record("r2", "")));
    }

    @Test
    public void getLoggedInUser() {
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        User loggedInUser = treeParser.getLoggedInUser();

        assertEquals("patient", loggedInUser.getUserId());
    }

    @Test
    public void getCurrentUserContext() {
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        Patient patientContext = treeParser.getCurrentPatientContext();

        assertEquals("patient", patientContext.getUserId());
    }

    @Test
    public void getCurrentProblemContext() {
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        Problem problemContext = treeParser.getCurrentProblemContext();

        assertEquals("p1", problemContext.getTitle());
        assertEquals("", problemContext.getDescription());
    }

    @Test
    public void getCurrentRecordContext() {
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        Record record = treeParser.getCurrentRecordContext();

        assertEquals("r2", record.getTitle());
        assertEquals("", record.getComment());
    }

    @Test
    public void getCurrentRecordContext2() {

        tree.pop();

        ContextTreeParser treeParser = new ContextTreeParser(tree);

        Record record = treeParser.getCurrentRecordContext();

        assertEquals(null, record);
    }

    @Test
    public void getCurrentBodyLocation() {
    }

    @Test
    public void getCurrentBodyPhoto() {
    }
}