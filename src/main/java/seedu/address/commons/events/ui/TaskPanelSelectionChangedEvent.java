package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyTask;

//@@author A0148038A
/**
 * Represents a selection change in the Person List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newTaskSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newTaskSelection) {
        this.newTaskSelection = newTaskSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newTaskSelection;
    }
}
