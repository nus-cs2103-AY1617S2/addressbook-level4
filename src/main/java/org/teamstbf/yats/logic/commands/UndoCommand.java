package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.UniqueEventList;

//@@author A0102778B

/**
 * Undoes the last option that mutated the save state of the data
 */
public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "UNDONE!";

    public UndoCommand() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public CommandResult execute() throws CommandException {
        // TODO Auto-generated method stub
        assert model != null;
        model.getPreviousState();
        return new CommandResult(String.format(MESSAGE_UNDO_TASK_SUCCESS));
   
    }
}
