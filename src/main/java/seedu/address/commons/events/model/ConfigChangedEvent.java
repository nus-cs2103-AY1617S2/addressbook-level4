package seedu.address.commons.events.model;

import seedu.address.commons.core.Config;
import seedu.address.commons.events.BaseEvent;

/** Indicates the configuration in the model has changed*/
public class ConfigChangedEvent extends BaseEvent {

    public final Config data;

    public ConfigChangedEvent(Config data) {
        this.data = data;
    }
//@@author A0121668A
    @Override
    public String toString() {
        return data.toString();
    }
}
