package seedu.geekeep.commons.events.model;

import seedu.geekeep.commons.events.BaseEvent;
import seedu.geekeep.model.ReadOnlyGeeKeep;

/** Indicates the GeeKeep in the model has changed*/
public class GeekeepFilePathChangedEvent extends BaseEvent {

    public final String filePath;
    public final ReadOnlyGeeKeep geekeep;

    public GeekeepFilePathChangedEvent(String filePath, ReadOnlyGeeKeep geekeep) {
        this.filePath = filePath;
        this.geekeep = geekeep;
    }

    @Override
    public String toString() {
        return "geekeepFilePath = " + filePath + "\nnumber of tasks " + geekeep.getTaskList().size()
                + ", number of tags " + geekeep.getTagList().size();
    }
}
