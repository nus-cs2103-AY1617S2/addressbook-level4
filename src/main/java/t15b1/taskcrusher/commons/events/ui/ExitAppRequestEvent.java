package t15b1.taskcrusher.commons.events.ui;

import t15b1.taskcrusher.commons.events.BaseEvent;

/**
 * Indicates a request for App termination
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
