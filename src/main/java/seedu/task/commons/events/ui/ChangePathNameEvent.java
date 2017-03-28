package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

public class ChangePathNameEvent extends BaseEvent {
	private String pathName;
	public ChangePathNameEvent(String pathName) {
		this.pathName = pathName;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	public String getPathName() {
		return pathName;
	}

}
