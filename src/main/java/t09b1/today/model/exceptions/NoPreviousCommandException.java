package t09b1.today.model.exceptions;

import t09b1.today.model.Model;

/**
 * Represents an error when {@link Model} tries to reverse a command when no
 * commands have been entered.
 */
public class NoPreviousCommandException extends Exception {
    public NoPreviousCommandException(String message) {
        super(message);
    }
}
