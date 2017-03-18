package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
			+ "Parameters: TASKNAME d/date s/startTime e/endTime m/description\n" + "Example: " + COMMAND_WORD
			+ "Example task d/090317 s/0946 e/1200 m/sample message";

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

	private final Task toAdd;

	/**
	 * Creates an AddCommand using raw values.
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */
	public AddCommand(String taskName, String taskDate, String taskStartTime, String taskEndTime,
			String taskDescription) throws IllegalValueException {
		this.toAdd = new Task(new TaskName(taskName), new TaskDate(taskDate), new TaskTime(taskStartTime),
				new TaskTime(taskEndTime), new String(taskDescription));
	}

	@Override
	public CommandResult execute() throws CommandException {
		assert model != null;
		try {
			model.addTask(toAdd);
			return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		} catch (UniqueTaskList.DuplicateTaskException e) {
			throw new CommandException(MESSAGE_DUPLICATE_TASK);
		}

	}

}
