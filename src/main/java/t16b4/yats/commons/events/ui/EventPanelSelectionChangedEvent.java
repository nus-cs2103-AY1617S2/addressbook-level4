package t16b4.yats.commons.events.ui;

import t16b4.yats.commons.events.BaseEvent;
import t16b4.yats.model.item.ReadOnlyEvent;
import t16b4.yats.model.item.ReadOnlyItem;

/**
 * Represents a selection change in the Event List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyEvent newSelection;

    public EventPanelSelectionChangedEvent(ReadOnlyEvent newValue) {
        this.newSelection = newValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyEvent getNewSelection() {
        return newSelection;
    }
}
