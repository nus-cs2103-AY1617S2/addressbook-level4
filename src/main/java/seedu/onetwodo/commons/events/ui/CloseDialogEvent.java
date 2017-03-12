package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;

public class CloseDialogEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
