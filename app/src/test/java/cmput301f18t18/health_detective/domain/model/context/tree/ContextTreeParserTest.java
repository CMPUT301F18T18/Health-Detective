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
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.factory.ContextTreeComponentFactory;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.PatientContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.ProblemContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.RecordContext;

import static org.junit.Assert.*;

public class ContextTreeParserTest {

    ContextTree tree;

    @Before
    public void init() {
        tree = new ContextTree();

        tree.push(ContextTreeComponentFactory.getContextComponent(new Patient("patient"), tree));
        tree.push(ContextTreeComponentFactory.getContextComponent(new Record("r1", ""),tree));
        tree.push(ContextTreeComponentFactory.getContextComponent(new Patient("patient2"), tree));
        tree.push(ContextTreeComponentFactory.getContextComponent(new Problem("p1", ""), tree));
        tree.push(ContextTreeComponentFactory.getContextComponent(new Record("r2", ""), tree));
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

        assertEquals("r1", record.getTitle());
        assertEquals("", record.getComment());
    }

    @Test
    public void getCurrentRecordContext2() {
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        Record record = treeParser.getCurrentRecordContext();

        assertEquals("r2", record.getTitle());
        assertEquals("", record.getComment());
    }

    @Test
    public void getCurrentRecordContext3() {
        tree.nuke();
        ContextTreeParser treeParser = new ContextTreeParser(tree);

        Record record = treeParser.getCurrentRecordContext();

        assertEquals( null, record);
    }

    @Test
    public void getHead() {
        ContextTreeComponent context = tree.getHead();

        Record record = new Record("r2", "");

        assertEquals(RecordContext.class, context.getClass());
        assertEquals(((RecordContext)context).getRecord().getTitle(), record.getTitle());
    }

    @Test
    public void getTail() {
        ContextTreeComponent context = tree.getHead();

        Patient patient = new Patient("patient");

        assertEquals(PatientContext.class, context.getClass());
        assertEquals(((PatientContext)context).getPatient().getUserId(), patient.getUserId());
    }

    @Test
    public void push() {
        ContextTreeComponent newComponent = new ProblemContext(new Problem("title3", ""));

        tree.push(newComponent);

        ContextTreeComponent component = tree.getHead();
        assertEquals(ProblemContext.class, component.getClass());
        assertEquals(((ProblemContext)component).getProblem().getTitle(), "title3");
    }

    @Test
    public void nuke() {
        tree.nuke();

        assertEquals(null, tree.getHead());
        assertEquals(null, tree.getTail());
    }
}