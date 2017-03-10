package seedu.bullletjournal.commons.events.model;

import seedu.bullletjournal.commons.events.BaseEvent;
import seedu.bullletjournal.model.ReadOnlyTodoList;

/** Indicates the AddressBook in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTodoList data;

    public TodoListChangedEvent(ReadOnlyTodoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
