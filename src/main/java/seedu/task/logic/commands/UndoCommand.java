package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoCommand extends Command {
	public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNDO_SUCCESS = "Deleted Task: %1$s";


    @Override
    public CommandResult execute() throws CommandException {

    	String previousCommand = model.previousCommand();
    	Task previousTask = model.previousTask();

        switch (previousCommand){
        case "Add": try {
				model.deleteTask(previousTask);
			} catch (TaskNotFoundException pnfe) {
				assert false : "The target task cannot be missing";
			}
        }

        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

}
