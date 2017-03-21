package seedu.doit.logic.commands;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.exceptions.CommandException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Task undid: %1$s";
    public static final String MESSAGE_FAILURE = "Unable to undo: ";

    //public static Command toUndo;

    /**
     * Creates an AddCommand using raw values for task.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.undo();
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
