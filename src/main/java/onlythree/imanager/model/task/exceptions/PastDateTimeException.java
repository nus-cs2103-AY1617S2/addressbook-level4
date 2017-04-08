package onlythree.imanager.model.task.exceptions;

//@@author A0140023E
/**
 * Signals that a date constructed was in the past.
 */
public class PastDateTimeException extends IllegalDateTimeException {

    /**
     * @param message should contain relevant information on why the date should not be in the past.
     */
    public PastDateTimeException(String message) {
        super(message);
    }

}
