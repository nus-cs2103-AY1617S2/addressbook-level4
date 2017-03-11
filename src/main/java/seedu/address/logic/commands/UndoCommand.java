package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Instruction;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;


/**
 * Adds a task to the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo a task. ";

    public static final String MESSAGE_SUCCESS = "Undone";
    public static final String MESSAGE_DUPLICATE_TASK = "Unable to undo";
    /*
    private final Task toAdd;
	*/
    /**
     * Creates an Undo command.
     */
    public UndoCommand() {
    	/*
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        toAdd = null;
        */
    }

    @Override
    public CommandResult execute() throws CommandException {
    	/*
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
		*/
    	
    	// Dummy return
    	return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}
