package cmput301f18t18.health_detective.domain.model.context.component.impl;

import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.component.base.AbstractContextTreeComponent;

public class RecordContext extends AbstractContextTreeComponent {

    private final Record record;

    public RecordContext(Record record) {
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }
}
