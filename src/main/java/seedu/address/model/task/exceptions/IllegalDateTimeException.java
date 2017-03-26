package seedu.address.model.task.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0140023E
/**
 * Signals that some given DateTime(s) does not fulfill some date related constraints.
 */
public abstract class IllegalDateTimeException extends IllegalValueException {

    /**
     * @param message should contain relevant information on the failed date related constraint(s)
     */
    public IllegalDateTimeException(String message) {
        super(message);
    }

}
