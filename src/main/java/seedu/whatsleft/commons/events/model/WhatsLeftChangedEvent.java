package seedu.whatsleft.commons.events.model;

import seedu.whatsleft.commons.events.BaseEvent;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;

/** Indicates the WhatsLeft in the model has changed*/
public class WhatsLeftChangedEvent extends BaseEvent {

    public final ReadOnlyWhatsLeft data;

    public WhatsLeftChangedEvent(ReadOnlyWhatsLeft data) {
        this.data = data;
    }
//@@author A0121668A
    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size()
                + "," + " number of events " + data.getEventList().size() + ", "
                + "number of tags " + data.getTagList().size();
    }
}
