package seedu.address.model.task.exceptions;

//@@author A0140023E
/**
 * Signals that the duration between two dates are negative or zero, thus constituting an invalid duration.
 */
public class InvalidDurationException extends IllegalDateTimeException {

    /**
     * @param message should contain relevant information on how the two dates have an
     *        invalid duration between them
     */
    public InvalidDurationException(String message) {
        super(message);
    }

}
