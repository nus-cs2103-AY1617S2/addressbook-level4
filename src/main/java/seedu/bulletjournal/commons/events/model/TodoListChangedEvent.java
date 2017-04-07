package seedu.bulletjournal.commons.events.model;

import seedu.bulletjournal.commons.events.BaseEvent;
import seedu.bulletjournal.model.ReadOnlyTodoList;

/** Indicates the TodoList in the model has changed */
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTodoList data;
    public final String commandText;

    public TodoListChangedEvent(ReadOnlyTodoList data, String commandText) {
        this.data = data;
        this.commandText = commandText;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTodoList().size() + ", number of tags " + data.getTagList().size();
    }
}
