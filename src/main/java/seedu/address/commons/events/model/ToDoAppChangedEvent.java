package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyToDoApp;;

/** Indicates the AddressBook in the model has changed*/
public class ToDoAppChangedEvent extends BaseEvent {

    public final ReadOnlyToDoApp data;

    public ToDoAppChangedEvent(ReadOnlyToDoApp data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
