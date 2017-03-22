package org.teamstbf.yats.model.item;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's completeness in the Task Manager.
 * is valid as declared in {@link #isValidPeriod(String)}
 */
//@author A0139448U
public class IsDone {
	
	private final String ISDONE_NOTDONE = "not done";
	private final String ISDONE_DONE = "done";
	
	private boolean isDone;
	public String value;

	public IsDone() {
		this.isDone = false;
		this.value = ISDONE_NOTDONE;
	}
	
	public IsDone(String done) throws IllegalValueException{
		assert done != null;
		this.value = done;
		if (this.value == ISDONE_DONE) {
			this.isDone = true;
		}
		if (this.value == ISDONE_NOTDONE) {
			this.isDone = false;
		}
	}
	
	public boolean getIsDone() {
		return isDone;
	}
	
	public void markDone() {
		this.isDone = true;
		this.value = ISDONE_DONE;
	}
}
