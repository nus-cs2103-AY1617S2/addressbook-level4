package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
@SuppressWarnings("serial")
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
