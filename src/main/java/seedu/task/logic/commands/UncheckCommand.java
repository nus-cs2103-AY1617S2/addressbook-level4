package seedu.task.logic.commands;

import java.util.List;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

public class UncheckCommand extends TaskCompleted {
	public static final String COMMAND_WORD = "unchecked";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark task completion status to unchecked/incompleted.\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_UNCHECK_SUCCESS = "Task %1$s unchecked/incomplete!";
    public static final String MESSAGE_TASK_ALREADY_UNCHECKED = "Task %1$s is already unchecked.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final int filteredTaskListIndex;


	public UncheckCommand (int filteredTaskListIndex) {
		this.filteredTaskListIndex = filteredTaskListIndex - 1;
	}

	@Override
	public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMarkComplete = lastShownList.get(filteredTaskListIndex);

        if (taskToMarkComplete.getCompletionStatus().getStatus()==false){
        	throw new CommandException(String.format(MESSAGE_TASK_ALREADY_UNCHECKED, taskToMarkComplete.getName()));
        }

        Task completedTask = changeTaskCompletion(taskToMarkComplete);

        try {
            model.updateTask(filteredTaskListIndex, completedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UNCHECK_SUCCESS, taskToMarkComplete.getName()));
	}
}
