package seedu.typed.commons.events.ui;

import seedu.typed.commons.events.BaseEvent;
import seedu.typed.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the T List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {

    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
