package seedu.doit.commons.events.ui;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class FloatingTaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyFloatingTask newSelection;

    public FloatingTaskPanelSelectionChangedEvent(ReadOnlyFloatingTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyFloatingTask getNewSelection() {
        return this.newSelection;
    }
}
