package t16b4.yats.commons.events.ui;

import t16b4.yats.commons.events.BaseEvent;
import t16b4.yats.model.item.ReadOnlyItem;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyItem newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyItem newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyItem getNewSelection() {
        return newSelection;
    }
}
