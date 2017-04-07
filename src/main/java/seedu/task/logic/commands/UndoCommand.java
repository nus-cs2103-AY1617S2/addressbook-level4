package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.UniqueTaskList;

//@@author A0163845X
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo successful";
    public static final String MESSAGE_FAIL = "Undo failure";

    @Override
    public CommandResult execute() throws CommandException {
	assert model != null;
	try {
	    model.undo();
	    return new CommandResult(String.format(MESSAGE_SUCCESS));
	} catch (Exception e) {
	    throw new CommandException(MESSAGE_FAIL);
	}
    }

}
