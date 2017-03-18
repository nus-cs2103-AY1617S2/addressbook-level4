package seedu.doit.commons.events.model;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyItemManager;

/**
 * Indicates the TaskManager in the model has changed
 */
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyItemManager data;

    public TaskManagerChangedEvent(ReadOnlyItemManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
