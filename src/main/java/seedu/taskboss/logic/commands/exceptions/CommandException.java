package seedu.taskboss.logic.commands.exceptions;

import seedu.taskboss.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
