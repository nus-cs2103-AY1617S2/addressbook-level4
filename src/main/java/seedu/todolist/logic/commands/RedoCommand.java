package seedu.todolist.logic.commands;

import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.Model;

public class RedoCommand extends Command {
    //@@author A0163786N
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes previous undo. ";

    public static final String MESSAGE_SUCCESS = "Undo has been reverted";

    public static final String MESSAGE_NO_ACTION = "Error: no previous undo to revert";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.loadNextState();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (Model.NoNextStateException e) {
            throw new CommandException(MESSAGE_NO_ACTION);
        }
    }
}
