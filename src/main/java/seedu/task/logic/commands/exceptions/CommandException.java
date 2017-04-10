package seedu.task.logic.commands.exceptions;

import seedu.task.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CommandException(String message) {
        super(message);
    }
}
