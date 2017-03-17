package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.todo.ReadOnlyTodo;

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
