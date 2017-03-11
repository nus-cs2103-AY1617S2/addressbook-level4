package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.EndDateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDateTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;


public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the last mutable action performed. ";


    public static final String MESSAGE_SUCCESS = "Last action undid";
    public static final String MESSAGE_NO_LAST_ACTION = "No previous action has been performed. ";

    @Override
    public CommandResult execute() throws CommandException {
	// TODO Auto-generated method stub
	return null;
    }

}
