//@@author A0164212U
package seedu.task.commons.exceptions;

/**
 * Signals that start timing is after end timing.
 */
public class IllegalTimingOrderException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalTimingOrderException(String message) {
        super(message);
    }
}
