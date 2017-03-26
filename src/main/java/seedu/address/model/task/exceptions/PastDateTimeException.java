package seedu.address.model.task.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0140023E
/**
 * Signals that a date constructed was in the past.
 */
public class PastDateTimeException extends IllegalValueException {

    /**
     * @param message should contain relevant information on why the date should not be in the past.
     */
    public PastDateTimeException(String message) {
        super(message);
    }

}
