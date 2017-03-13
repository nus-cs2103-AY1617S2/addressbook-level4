package seedu.doit.commons.events.model;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyFloatingTaskManager;

/**
 * Indicates the FloatingTaskManager in the model has changed
 */
public class FloatingTaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyFloatingTaskManager data;

    public FloatingTaskManagerChangedEvent(ReadOnlyFloatingTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of floatingTasks " + data.getFloatingTaskList().size()
                + ", number of tags " + data.getTagList().size();
    }
}
