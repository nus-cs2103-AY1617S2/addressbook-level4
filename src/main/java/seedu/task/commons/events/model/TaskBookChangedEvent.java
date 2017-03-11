package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskList;

/** Indicates the AddressBook in the model has changed*/
public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public TaskBookChangedEvent(ReadOnlyTaskList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
