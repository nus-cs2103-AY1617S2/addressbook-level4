package org.teamstbf.yats.model.item;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's completeness in the Task Manager.
 */
// @@author A0139448U
public class IsDone {

    public static final String MESSAGE_ISDONE_CONSTRAINTS = "IsDone must be Yes or No";
    public static final String ISDONE_VALIDATION_REGEX = ".*(Yes|No).*";

    public final static String ISDONE_NOTDONE = "No";
    public final static String ISDONE_DONE = "Yes";

    private String value;

    public IsDone() {
	this.value = ISDONE_NOTDONE;
    }

    public IsDone(String done) throws IllegalValueException {
	String trimmedIsDone = done.trim();
	if (!isValidIsDone(trimmedIsDone)) {
	    throw new IllegalValueException(MESSAGE_ISDONE_CONSTRAINTS);
	}
	this.value = trimmedIsDone;
    }

    public static boolean isValidIsDone(String test) {
	return test.matches(ISDONE_VALIDATION_REGEX);
    }

    public String getValue() {
	return value;
    }

    public void markDone() {
	this.value = ISDONE_DONE;
    }

    public void markUndone() {
	this.value = ISDONE_NOTDONE;
    }
}
