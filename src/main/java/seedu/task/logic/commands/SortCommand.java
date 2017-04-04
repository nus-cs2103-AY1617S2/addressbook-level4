package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.TaskComparable;
import seedu.task.model.task.TaskCreationDateComparator;
import seedu.task.model.task.TaskNameComparable;
import seedu.task.model.task.TaskStatusComparable;
import seedu.task.model.task.TaskTimeComparable;

//@@author A0163845X
public class SortCommand extends Command {
	public static final String COMMAND_WORD = "sort";
	private String sortMethod;
	public static final String MESSAGE_USAGE = "can either use 'sort name' or 'sort time' or 'sort cd' or 'sort status'";
	public static final String MESSAGE_FAILURE = "Sorting failed";
	public static final String MESSAGE_SUCCESS = "Sorting success";

	public SortCommand(String sortMethod) {
		this.sortMethod = sortMethod;
	}

	public CommandResult execute() throws CommandException {
		// try {
		assert model != null;
		TaskComparable t;
		if (sortMethod.equals("name")) {
			t = new TaskNameComparable();
		} else if (sortMethod.equals("time")) {
			t = new TaskTimeComparable();
		} else if (sortMethod.equals("cd")) {
			t = new TaskCreationDateComparator();
		} else if (sortMethod.equals("status")) {
			t = new TaskStatusComparable();
		} else {
			return new CommandResult(MESSAGE_FAILURE);
		}
		model.sort(t);
		return new CommandResult(MESSAGE_SUCCESS);
		// } catch (Exception e) {
		// throw new CommandException(MESSAGE_FAILURE);
		// }
	}

	public boolean isUndoable() {
		return true;
	}

}
