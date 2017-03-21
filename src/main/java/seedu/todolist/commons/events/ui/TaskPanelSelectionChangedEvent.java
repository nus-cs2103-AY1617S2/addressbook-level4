package seedu.todolist.commons.events.ui;

import seedu.todolist.commons.events.BaseEvent;
import seedu.todolist.model.task.Task;

/**
 * Represents a selection change in the Person List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final Task newSelection;

    public TaskPanelSelectionChangedEvent(Task newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Task getNewSelection() {
        return newSelection;
    }
}
