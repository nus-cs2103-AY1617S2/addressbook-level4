package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

public class SortCommand extends Command {
	public static final String COMMAND_WORD = "sort";
	private String sortMethod;
	public static final String MESSAGE_USAGE = "can either use 'sort name' or 'sort time'";
	public static final String MESSAGE_FAILURE = "Sorting failed";
	public static final String MESSAGE_SUCCESS = "Sorting success";
	public SortCommand(String sortMethod) {
		this.sortMethod = sortMethod;
	}
	public CommandResult execute() throws CommandException {
		try {
			assert model != null;
			if (sortMethod.equals("name")) {
				model.sortByName();
			} else if (sortMethod.equals("time")) {
				model.sortByTime();
			} else {
				return new CommandResult(MESSAGE_FAILURE);
			}
			return new CommandResult(MESSAGE_SUCCESS);
		} catch (Exception e) {
			throw new CommandException(MESSAGE_FAILURE);
		}
	}
	public boolean isUndoable() {
		return true;
	}

}
