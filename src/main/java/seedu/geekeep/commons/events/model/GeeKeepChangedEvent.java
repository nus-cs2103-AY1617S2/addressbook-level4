package seedu.geekeep.commons.events.model;

import seedu.geekeep.commons.events.BaseEvent;
import seedu.geekeep.model.ReadOnlyGeeKeep;

/** Indicates the GeeKeep in the model has changed*/
public class GeeKeepChangedEvent extends BaseEvent {

    public final ReadOnlyGeeKeep data;

    public GeeKeepChangedEvent(ReadOnlyGeeKeep data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
