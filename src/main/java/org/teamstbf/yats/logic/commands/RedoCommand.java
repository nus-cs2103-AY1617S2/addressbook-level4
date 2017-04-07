package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.logic.commands.exceptions.CommandException;

//@@author A0102778B

/**
 * Undoes the last option that mutated the save state of the data
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Redo completed";
    public static final String MESSAGE_EMPTY_REDO_STACK = "Nothing to redo";

    public RedoCommand() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        if (model.checkEmptyRedoStack()) {
            throw new CommandException(MESSAGE_EMPTY_REDO_STACK);
        }
        model.getNextState();
        return new CommandResult(String.format(MESSAGE_UNDO_TASK_SUCCESS));
    }
}
