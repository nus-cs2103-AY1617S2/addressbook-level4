package seedu.today.commons.events.ui;

import seedu.today.commons.events.BaseEvent;

public class UpdateStatusBarEvent extends BaseEvent {
    public final String message;

    // @@author A0144315N
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
