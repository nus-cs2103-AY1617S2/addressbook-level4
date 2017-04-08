package t09b1.today.commons.events.ui;

import t09b1.today.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final String targetIndex;

    public JumpToListRequestEvent(String targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
