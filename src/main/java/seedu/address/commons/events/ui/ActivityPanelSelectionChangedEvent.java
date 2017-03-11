package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyActivity;

/**
 * Represents a selection change in the Person List Panel
 */
public class ActivityPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyActivity newSelection;

    public ActivityPanelSelectionChangedEvent(ReadOnlyActivity newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyActivity getNewSelection() {
        return newSelection;
    }
}
