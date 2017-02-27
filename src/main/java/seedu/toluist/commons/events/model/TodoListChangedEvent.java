package seedu.toluist.commons.events.model;

import seedu.toluist.commons.events.BaseEvent;
import seedu.toluist.model.TodoList;

/** Indicates the AddressBook in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final TodoList data;

    public TodoListChangedEvent (TodoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTasks().size();
    }
}
