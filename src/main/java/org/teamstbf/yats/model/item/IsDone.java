package org.teamstbf.yats.model.item;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's completeness in the Task Manager.
 * is valid as declared in {@link #isValidPeriod(String)}
 */
//@author A0139448U
public class IsDone {

	public final static String ISDONE_NOTDONE = "No";
	public final static String ISDONE_DONE = "Yes";

	private String value;

	public IsDone() {
		this.value = ISDONE_NOTDONE;
	}

	public IsDone(String done) throws IllegalValueException{
		assert done != null;
		this.value = done;
	}

	public String getValue() {
		return value;
	}

	public void markDone() {
		this.value = ISDONE_DONE;
	}
}
