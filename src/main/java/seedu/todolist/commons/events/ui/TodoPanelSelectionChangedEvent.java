package seedu.todolist.commons.events.ui;

import seedu.todolist.commons.events.BaseEvent;
import seedu.todolist.model.todo.ReadOnlyTodo;

/**
 * Represents a selection change in the Todo List Panel
 */
public class TodoPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTodo newSelection;

    public TodoPanelSelectionChangedEvent(ReadOnlyTodo newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTodo getNewSelection() {
        return newSelection;
    }
}
