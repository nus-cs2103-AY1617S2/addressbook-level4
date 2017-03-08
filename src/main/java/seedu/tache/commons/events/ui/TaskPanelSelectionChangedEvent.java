package seedu.tache.commons.events.ui;

import seedu.tache.commons.events.BaseEvent;
import seedu.tache.model.task.FloatingTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final FloatingTask newSelection;

    public TaskPanelSelectionChangedEvent(FloatingTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public FloatingTask getNewSelection() {
        return newSelection;
    }
}
