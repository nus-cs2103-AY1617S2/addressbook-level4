package org.teamstbf.yats.commons.events.ui;

import org.teamstbf.yats.commons.events.BaseEvent;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

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
