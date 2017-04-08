package t09b1.today.commons.events.ui;

import t09b1.today.commons.events.BaseEvent;

//@@author A0093999Y
/**
 * Informs the Completed Task Panel whether to Show, Hide, or Toggle.
 */
public class ShowCompletedTaskEvent extends BaseEvent {

    public enum Action {
        SHOW, HIDE, TOGGLE
    };

    public final Action action;

    public ShowCompletedTaskEvent(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
