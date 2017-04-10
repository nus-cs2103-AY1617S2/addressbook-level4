package seedu.taskit.commons.events.ui;

import seedu.taskit.commons.events.BaseEvent;

//@@author A0141872E
/**
* Represents a selection change in the Menu Bar Panel
*/
public class MenuBarPanelSelectionChangedEvent extends BaseEvent {

    private final String newSelection;

    public MenuBarPanelSelectionChangedEvent(String newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getNewSelection() {
        return newSelection;
    }

}
