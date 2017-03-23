package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyWhatsLeft;

/** Indicates the WhatsLeft in the model has changed*/
public class WhatsLeftChangedEvent extends BaseEvent {

    public final ReadOnlyWhatsLeft data;

    public WhatsLeftChangedEvent(ReadOnlyWhatsLeft data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks and events " + (data.getTaskList().size() + data.getEventList().size()) + ","
                + "number of tags " + data.getTagList().size();
    }
}
