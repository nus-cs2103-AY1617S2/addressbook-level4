package seedu.today.commons.events.ui;

import seedu.today.commons.events.BaseEvent;

public class UIAniminationEndEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
