package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class UpdateStatusBarEvent extends BaseEvent {
    public final String message;

    public UpdateStatusBarEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "[UpdateStatusBarEvent]" + message;
    }
}
