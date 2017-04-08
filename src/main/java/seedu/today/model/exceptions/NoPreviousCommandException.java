package seedu.today.model.exceptions;

import seedu.today.model.Model;

/**
 * Represents an error when {@link Model} tries to reverse a command when no
 * commands have been entered.
 */
public class NoPreviousCommandException extends Exception {
    public NoPreviousCommandException(String message) {
        super(message);
    }
}
