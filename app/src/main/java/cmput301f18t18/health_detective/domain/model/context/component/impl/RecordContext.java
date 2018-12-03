package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

/**
 * Record context tree component, stores a record in the context tree
 */
public class RecordContext extends AbstractContextTreeComponent {

    private final Record record;

    public RecordContext(Record record) {
        super();
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }
}
