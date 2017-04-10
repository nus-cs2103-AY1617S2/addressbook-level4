package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;

public class DeselectCardsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

