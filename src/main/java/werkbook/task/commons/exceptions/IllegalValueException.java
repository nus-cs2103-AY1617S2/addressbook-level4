package werkbook.task.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }
}
