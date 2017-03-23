package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;

/**
 * Represents a selection change in the Person List Panel
 */
public class ActivityPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyEvent newEventSelection;
    private final ReadOnlyTask newTaskSelection;

    public ActivityPanelSelectionChangedEvent(ReadOnlyEvent newEventSelection) {
        this.newEventSelection = newEventSelection;
        this.newTaskSelection = null;
    }
    
    public ActivityPanelSelectionChangedEvent(ReadOnlyTask newTaskSelection) {
        this.newTaskSelection = newTaskSelection;
        this.newEventSelection = null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyEvent getNewSelection() {
        return newEventSelection;
    }
}
