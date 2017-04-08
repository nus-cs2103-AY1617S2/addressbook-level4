package seedu.doist.commons.events.model;

import seedu.doist.commons.events.BaseEvent;
import seedu.doist.model.ReadOnlyAliasListMap;

//@@author A0140887W
/** Indicates the AliasListMap in the model has changed*/
public class AliasListMapChangedEvent extends BaseEvent {

    public final ReadOnlyAliasListMap data;

    public AliasListMapChangedEvent(ReadOnlyAliasListMap data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
