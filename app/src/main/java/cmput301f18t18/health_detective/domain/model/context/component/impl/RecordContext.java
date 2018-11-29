package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class RecordContext extends AbstractContextTreeComponent {

    private final Record record;

    public RecordContext(Class<? extends AbstractInteractor> commandContext, Record record) {
        super(commandContext);
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }
}
