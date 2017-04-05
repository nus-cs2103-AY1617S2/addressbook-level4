package seedu.whatsleft.commons.events.ui;

import seedu.whatsleft.commons.events.BaseEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;

//@@author A0148038A
/**
 * Represents a selection change in the Task List Panel
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
