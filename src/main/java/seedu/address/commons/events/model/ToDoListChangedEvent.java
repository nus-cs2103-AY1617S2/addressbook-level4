package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyToDoList;

/** Indicates the AddressBook in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public ToDoListChangedEvent(ReadOnlyToDoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
