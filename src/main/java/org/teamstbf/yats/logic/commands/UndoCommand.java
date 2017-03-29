package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.UniqueEventList;

//@@author A0102778B

/**
 * Undoes the last option that mutated the save state of the data.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo completed";
    public static final String MESSAGE_EMPTY_UNDO_STACK = "Nothing to undo";

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        if (model.checkEmptyUndoStack()) {
            throw new CommandException(MESSAGE_EMPTY_UNDO_STACK);
        }
        model.getPreviousState();
        return new CommandResult(String.format(MESSAGE_UNDO_TASK_SUCCESS));

    }
}
