package org.teamstbf.yats.commons.events.ui;

import org.teamstbf.yats.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;

    public NewResultAvailableEvent(String message) {
	this.message = message;
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName();
    }

}
