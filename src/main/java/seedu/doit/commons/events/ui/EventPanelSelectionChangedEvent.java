package seedu.doit.commons.events.ui;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.item.ReadOnlyEvent;

/**
 * Represents a selection change in the Task List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyEvent newSelection;

    public EventPanelSelectionChangedEvent(ReadOnlyEvent newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyEvent getNewSelection() {
        return this.newSelection;
    }
}
