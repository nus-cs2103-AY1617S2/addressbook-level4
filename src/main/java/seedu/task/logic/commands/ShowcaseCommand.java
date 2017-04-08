package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0163845X
public class ShowcaseCommand extends Command {

	private int numberOfTasks;
	private String MESSAGE_SUCCESS = "Sample data generated";
	private String MESSAGE_FAILURE = "Sample data failed to generate";
	public static final String COMMAND_WORD = "showcase";
	public ShowcaseCommand (int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}
	@Override
	public CommandResult execute() throws CommandException {
		assert model != null;
		try {
			model.showcase(numberOfTasks);
			return new CommandResult(MESSAGE_SUCCESS);
		} catch (DuplicateTaskException dte) {
			return new CommandResult(MESSAGE_FAILURE);
		}
	}

	public boolean isUndoable() {
		return true;
	}
}
