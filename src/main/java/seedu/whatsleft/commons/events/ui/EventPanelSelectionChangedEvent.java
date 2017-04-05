package seedu.whatsleft.commons.events.ui;

import seedu.whatsleft.commons.events.BaseEvent;
import seedu.whatsleft.model.activity.ReadOnlyEvent;

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
