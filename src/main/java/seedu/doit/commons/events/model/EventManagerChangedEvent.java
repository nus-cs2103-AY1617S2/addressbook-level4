package seedu.doit.commons.events.model;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyEventManager;

/**
 * Indicates the EventManager in the model has changed
 */
public class EventManagerChangedEvent extends BaseEvent {

    public final ReadOnlyEventManager data;

    public EventManagerChangedEvent(ReadOnlyEventManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size()
                + ", number of tags " + data.getTagList().size();
    }
}
