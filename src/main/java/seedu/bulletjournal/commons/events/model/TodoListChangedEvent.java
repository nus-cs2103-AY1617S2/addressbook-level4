package seedu.bulletjournal.commons.events.model;

import seedu.bulletjournal.commons.events.BaseEvent;
import seedu.bulletjournal.model.ReadOnlyTodoList;

/** Indicates the TodoList in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTodoList data;

    public TodoListChangedEvent(ReadOnlyTodoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
