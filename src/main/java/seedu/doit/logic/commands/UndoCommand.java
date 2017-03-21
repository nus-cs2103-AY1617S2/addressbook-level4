package seedu.doit.logic.commands;

import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;
import seedu.doit.logic.commands.exceptions.CommandException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Task undid: %1$s";
    public static final String MESSAGE_FAILURE = "Unable to undo. You cannot undo a save, find and list";

    // public static Command toUndo;

    /**
     * Creates an UndoCommand
     */
    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.undo();
            return new CommandResult(String.format(MESSAGE_SUCCESS, "dummy"));
        } catch (EmptyTaskManagerStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
