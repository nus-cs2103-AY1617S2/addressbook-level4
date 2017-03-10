package t15b1.taskcrusher.commons.events.ui;

import t15b1.taskcrusher.commons.events.BaseEvent;
import t15b1.taskcrusher.model.task.ReadOnlyTask;

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
