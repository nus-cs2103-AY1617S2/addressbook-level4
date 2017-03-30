package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskStatus;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
	    + "Parameters: TaskName [d/date1 [date2]] [s/startTime] [e/endTime] [m/description] - all items in [] are optional \n"
	    + "Example: " + COMMAND_WORD + " Example d/090317 s/09:45 e/12:00 m/Sample Message";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task taskToAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String taskName, String taskDate, String taskStartTime, String taskEndTime,
	    String taskDescription, Set<String> tags) throws IllegalValueException {
	final Set<Tag> tagSet = new HashSet<>();
	for (String tagName : tags) {
	    tagSet.add(new Tag(tagName));
	}
	this.taskToAdd = new Task(new TaskName(taskName), new TaskDate(taskDate), new TaskTime(taskStartTime),

		new TaskTime(taskEndTime), new String(taskDescription),new TaskStatus("Ongoing"), new UniqueTagList(tagSet));

    }
    //@@author A0163845X
    public AddCommand(TaskName parseTaskName, Optional<TaskDate> parseDate, Optional<TaskTime> parseStartTime,
	    Optional<TaskTime> parseEndTime, Optional<String> parseString) throws IllegalValueException {
	this.taskToAdd = new Task(parseTaskName, parseDate, parseStartTime, parseEndTime, parseString, new TaskStatus("Ongoing"));

    }

    @Override
    public CommandResult execute() throws CommandException {
	assert model != null;
	try {
	    model.addTask(taskToAdd);
	    return new CommandResult(String.format(MESSAGE_SUCCESS, taskToAdd));
	} catch (UniqueTaskList.DuplicateTaskException e) {
	    throw new CommandException(MESSAGE_DUPLICATE_TASK);
	}

    }
    
    public boolean isUndoable () {
    	return true;
    }

}
