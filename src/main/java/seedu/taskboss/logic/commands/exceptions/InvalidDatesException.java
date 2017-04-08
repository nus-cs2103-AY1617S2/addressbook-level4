//@@author A0143157J
package seedu.taskboss.logic.commands.exceptions;

/**
 * Signals an error caused by having a task's end date
 * earlier than its start date
 */
public class InvalidDatesException extends Exception {
    public InvalidDatesException(String message) {
        super(message);
    }

}
