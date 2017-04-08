package seedu.taskmanager.commons.events.ui;

import seedu.taskmanager.commons.events.BaseEvent;

// @@author A0131278H
/**
 * Represents a selection change in the Task List Tab Pane
 */
public class TabSelectionChangedEvent extends BaseEvent {
    
    public final int targetIndex;

    public TabSelectionChangedEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
// @@author
