package seedu.watodo.commons.events.model;

import seedu.watodo.commons.events.BaseEvent;
import seedu.watodo.model.ReadOnlyTaskManager;

/** Indicates the AddressBook in the model has changed*/
public class TaskListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskListChangedEvent(ReadOnlyTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
