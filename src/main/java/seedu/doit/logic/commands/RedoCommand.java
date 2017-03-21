package seedu.doit.logic.commands;

import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;
import seedu.doit.logic.commands.exceptions.CommandException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Task redid: %1$s";
    public static final String MESSAGE_FAILURE = "Unable to redo: ";

    // public static Command toRedo;

    /**
     * Creates an RedoCommand.
     */
    public RedoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.redo();
            return new CommandResult(String.format(MESSAGE_SUCCESS, "dummy"));
        } catch (EmptyTaskManagerStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
