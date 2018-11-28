package cmput301f18t18.health_detective.domain.model.context.component.factory;

import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.PatientContext;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.impl.ProblemContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.RecordContext;

public class ContextTreeComponentFactory {

    private ContextTreeComponentFactory() {}

    public static ContextTreeComponent getContextComponent(Object contextData) {
        if (contextData instanceof CareProvider) {
            return new CareProviderContext((CareProvider) contextData);
        }
        else if (contextData instanceof Patient) {
            return new PatientContext((Patient) contextData);
        }
        else if (contextData instanceof Problem) {
            return new ProblemContext((Problem) contextData);
        }
        else if (contextData instanceof Record) {
            return new RecordContext((Record) contextData);
        }

        return null;
    }
}
