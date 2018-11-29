package cmput301f18t18.health_detective.domain.model.context.component.factory;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.BodyLocation;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.impl.BodyImageContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.BodyLocationContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.CareProviderContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.PatientContext;
import cmput301f18t18.health_detective.domain.model.context.component.ContextTreeComponent;
import cmput301f18t18.health_detective.domain.model.context.component.impl.ProblemContext;
import cmput301f18t18.health_detective.domain.model.context.component.impl.RecordContext;
import cmput301f18t18.health_detective.domain.model.images.BodyImage;

public class ContextTreeComponentFactory {

    private ContextTreeComponentFactory() {}

    public static ContextTreeComponent getContextComponent(Class<? extends AbstractInteractor> commandContext, Object contextData) {
        // Determine type of context tree component to be generated based on type of provided data
        if (contextData instanceof CareProvider) {
            return new CareProviderContext(commandContext, (CareProvider) contextData);
        }
        else if (contextData instanceof Patient) {
            return new PatientContext(commandContext, (Patient) contextData);
        }
        else if (contextData instanceof Problem) {
            return new ProblemContext(commandContext, (Problem) contextData);
        }
        else if (contextData instanceof Record) {
            return new RecordContext(commandContext, (Record) contextData);
        }
        else if (contextData instanceof BodyLocation) {
            return new BodyLocationContext(commandContext, (BodyLocation) contextData);
        }
        else if (contextData instanceof BodyImage) {
            return new BodyImageContext(commandContext, (BodyImage) contextData);
        }

        return null;
    }
}
