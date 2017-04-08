package org.teamstbf.yats.commons.events.ui;

import org.teamstbf.yats.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

	public final int targetIndex;

	public JumpToListRequestEvent(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
