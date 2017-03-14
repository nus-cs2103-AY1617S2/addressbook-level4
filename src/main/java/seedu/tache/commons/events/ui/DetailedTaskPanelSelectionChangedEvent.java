package seedu.tache.commons.events.ui;

import seedu.tache.commons.events.BaseEvent;
import seedu.tache.model.task.ReadOnlyDetailedTask;

/**
 * Represents a selection change in the Detailed Task List Panel
 */
public class DetailedTaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyDetailedTask newSelection;

    public DetailedTaskPanelSelectionChangedEvent(ReadOnlyDetailedTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyDetailedTask getNewSelection() {
        return newSelection;
    }
}
