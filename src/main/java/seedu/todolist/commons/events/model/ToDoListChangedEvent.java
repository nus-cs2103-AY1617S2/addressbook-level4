package seedu.todolist.commons.events.model;

import seedu.todolist.commons.events.BaseEvent;
import seedu.todolist.model.ReadOnlyToDoList;

/** Indicates the AddressBook in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;
    public final int index;
    public final String typeOfCommand;

    public ToDoListChangedEvent(ReadOnlyToDoList data, int index, String typeOfCommand) {
        this.data = data;
        this.index = index;
        this.typeOfCommand = typeOfCommand;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
