package seedu.todolist.commons.events.model;

import seedu.todolist.commons.events.BaseEvent;
import seedu.todolist.model.ReadOnlyTodoList;

/** Indicates the TodoList in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTodoList data;

    public TodoListChangedEvent(ReadOnlyTodoList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of todos " + data.getTodoList().size() + ", number of tags " + data.getTagList().size();
    }
}
