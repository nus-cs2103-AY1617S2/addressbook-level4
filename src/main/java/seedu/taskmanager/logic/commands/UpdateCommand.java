package seedu.taskmanager.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.util.CollectionUtil;
//import seedu.taskmanager.commons.util.CurrentDate;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;
//import seedu.taskmanager.model.category.UniqueCategoryList;

/**
 * Updates the details of an existing task in the task manager.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "UPDATE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the task identified "
	    + "by the index number used in the last task listing. "
	    + "Existing values will be overwritten by the input values.\n"
	    + "Parameters: INDEX (must be a positive integer) [TASK] ON [DATE] FROM [STARTTIME] TO [ENDTIME]\n"
	    + "Example: " + COMMAND_WORD + " 1 ON 04/03/17 FROM 1630 TO 1830";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final int filteredTaskListIndex;
    private final UpdateTaskDescriptor updateTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to update
     * @param updateTaskDescriptor
     *            details to update the task with
     */
    public UpdateCommand(int filteredTaskListIndex, UpdateTaskDescriptor updateTaskDescriptor) {
	assert filteredTaskListIndex > 0;
	assert updateTaskDescriptor != null;

	// converts filteredTaskListIndex from one-based to zero-based.
	this.filteredTaskListIndex = filteredTaskListIndex - 1;

	this.updateTaskDescriptor = new UpdateTaskDescriptor(updateTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
	List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

	if (filteredTaskListIndex >= lastShownList.size()) {
	    throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	ReadOnlyTask taskToUpdate = lastShownList.get(filteredTaskListIndex);
	Task updatedTask = createUpdatedTask(taskToUpdate, updateTaskDescriptor);

	try {
	    model.updateTask(filteredTaskListIndex, updatedTask);
	} catch (UniqueTaskList.DuplicateTaskException dpe) {
	    throw new CommandException(MESSAGE_DUPLICATE_TASK);
	}

	model.updateFilteredListToShowAll();
	return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createUpdatedTask(ReadOnlyTask taskToUpdate, UpdateTaskDescriptor updateTaskDescriptor) {
	assert taskToUpdate != null;

	TaskName updatedTaskName = updateTaskDescriptor.getTaskName().orElseGet(taskToUpdate::getTaskName);
	Date updatedDate = updateTaskDescriptor.getDate().orElseGet(taskToUpdate::getDate);
	StartTime updatedStartTime = updateTaskDescriptor.getStartTime().orElseGet(taskToUpdate::getStartTime);
	EndTime updatedEndTime = updateTaskDescriptor.getEndTime().orElseGet(taskToUpdate::getEndTime);
	// UniqueCategoryList updatedCategories =
	// updateTaskDescriptor.getCategories().orElseGet(taskToUpdate::getCategories);

	return new Task(updatedTaskName, updatedDate, updatedStartTime,
		updatedEndTime/* , updatedCategories */);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
  public static class UpdateTaskDescriptor {
	private Optional<TaskName> taskname = Optional.empty();
	private Optional<Date> date = Optional.empty();
	private Optional<StartTime> starttime = Optional.empty();
	private Optional<EndTime> endtime = Optional.empty();
	// private Optional<UniqueCategoryList> categories = Optional.empty();

	public UpdateTaskDescriptor() {
	}

	public UpdateTaskDescriptor(UpdateTaskDescriptor toCopy) {
	    this.taskname = toCopy.getTaskName();
	    this.date = toCopy.getDate();
	    this.starttime = toCopy.getStartTime();
	    this.endtime = toCopy.getEndTime();
	    // this.categories = toCopy.getCategories();
	}

	/**
	 * Returns true if at least one field is updated.
	 */
	public boolean isAnyFieldUpdated() {
	    return CollectionUtil.isAnyPresent(this.taskname,
		    this.date/*
			      * , this.endtime, this.categories
			      */);
	}

	public void setTaskName(Optional<TaskName> taskname) {
	    assert taskname != null;
	    this.taskname = taskname;
	}

	public Optional<TaskName> getTaskName() {
	    return taskname;
	}

	public void setDate(Optional<Date> date) {
	    assert date != null;
	    this.date = date;
	}

	public Optional<Date> getDate() {
	    return date;
	}

	public void setStartTime(Optional<StartTime> starttime) {
	    assert starttime != null;
	    this.starttime = starttime;
	}

	public Optional<StartTime> getStartTime() {
	    return starttime;
	}

	public void setEndTime(Optional<EndTime> endtime) {
	    assert endtime != null;
	    this.endtime = endtime;
	}

	public Optional<EndTime> getEndTime() {
	    return endtime;
	}

	/*
	 * public void setCategories(Optional<UniqueCategoryList> categories) {
	 * assert categories != null; this.categories = categories; }
	 *
	 * public Optional<UniqueCategoryList> getCategories() { return
	 * categories; }
	 */
    }
}
