package org.teamstbf.yats.commons.events.ui;

import org.teamstbf.yats.commons.events.BaseEvent;

/**
 * Indicates a request for App termination
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
	return this.getClass().getSimpleName();
    }
}
