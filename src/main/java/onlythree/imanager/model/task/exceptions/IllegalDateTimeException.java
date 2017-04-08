package onlythree.imanager.model.task.exceptions;

//@@author A0140023E
/**
 * Signals that some given DateTime(s) does not fulfill some date related constraints.
 */
public abstract class IllegalDateTimeException extends Exception {

    /**
     * @param message should contain relevant information on the failed date related constraint(s)
     */
    public IllegalDateTimeException(String message) {
        super(message);
    }

}
