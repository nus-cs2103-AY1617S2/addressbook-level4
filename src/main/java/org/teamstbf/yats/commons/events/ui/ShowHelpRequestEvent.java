package org.teamstbf.yats.commons.events.ui;

import org.teamstbf.yats.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
	return this.getClass().getSimpleName();
    }

}
