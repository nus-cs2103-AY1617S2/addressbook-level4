package seedu.watodo.commons.events.ui;

import seedu.watodo.commons.events.BaseEvent;
import seedu.watodo.model.task.ReadOnlyFloatingTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyFloatingTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyFloatingTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyFloatingTask getNewSelection() {
        return newSelection;
    }
}
