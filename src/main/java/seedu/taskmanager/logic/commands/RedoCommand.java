package seedu.taskmanager.logic.commands;

import java.util.EmptyStackException;

import seedu.taskmanager.logic.commands.exceptions.CommandException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "REDO";
    public static final String MESSAGE_SUCCESS = "Previous action has been redone.";
    public static final String MESSAGE_FAILURE = "Nothing to redo.";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.redoTaskManager();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
