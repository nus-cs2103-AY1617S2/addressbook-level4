package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to expand a group
 */
public class ExpandingEvent extends BaseEvent {

    public final int groupIndex;

    public ExpandingEvent(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
