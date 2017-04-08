package t09b1.today.commons.events.ui;

import t09b1.today.commons.events.BaseEvent;

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
