package cmput301f18t18.health_detective.domain.interactors.impl;

import android.content.Context;

import cmput301f18t18.health_detective.domain.interactors.DeleteRecordPhoto;
import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;
import cmput301f18t18.health_detective.domain.model.DomainImage;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTree;
import cmput301f18t18.health_detective.domain.model.context.tree.ContextTreeParser;

public class DeletePhotoImpl extends AbstractInteractor implements DeleteRecordPhoto {

    private Callback callback;
    private DomainImage image;

    public DeletePhotoImpl(Callback callback, DomainImage image) {
        this.callback = callback;
        this.image = image;
    }


    @Override
    public void run() {
        if (image == null)
            return;

        ContextTree tree = context.getContextTree();
        ContextTreeParser treeParser = new ContextTreeParser(tree);
        Record recordContext = treeParser.getCurrentRecordContext();

        if (recordContext == null)
            return;

        recordContext.deletePhoto(image);

        new PutContext(recordContext);

        context.getRecordRepo().insertRecord(recordContext);
    }
}
