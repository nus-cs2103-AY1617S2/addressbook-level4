package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyEvent;

//@@author A0148038A
/**
 * Represents a selection change in the Person List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyEvent newEventSelection;

    public EventPanelSelectionChangedEvent(ReadOnlyEvent newEventSelection) {
        this.newEventSelection = newEventSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyEvent getNewSelection() {
        return newEventSelection;
    }
}
