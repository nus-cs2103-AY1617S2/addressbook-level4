package onlythree.imanager.commons.events.ui;

import onlythree.imanager.commons.events.BaseEvent;
import onlythree.imanager.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
