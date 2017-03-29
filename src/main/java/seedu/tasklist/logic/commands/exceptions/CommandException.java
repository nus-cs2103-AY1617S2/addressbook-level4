package seedu.tasklist.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
@SuppressWarnings("serial")
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
