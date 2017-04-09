

package seedu.taskit.commons.exceptions;

//@@author A0141011J
/**
 * Represents an error when there are no valid state to restore to
 *
 */

public class NoValidStateException extends Exception {
    public NoValidStateException() {
        super("No valid state to change to");
    }
}
