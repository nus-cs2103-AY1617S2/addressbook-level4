package seedu.doist.commons.events.model;

import seedu.doist.commons.events.BaseEvent;
import seedu.doist.model.ReadOnlyTodoList;

/** Indicates the AddressBook in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTodoList data;

    public TodoListChangedEvent(ReadOnlyTodoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
