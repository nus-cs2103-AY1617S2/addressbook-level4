package seedu.task.logic.commands;

import java.util.List;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

public class TaskCompletedCommand extends Command {

    public static final String COMMAND_WORD = "completed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change task status to completed\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task %1$s completed!";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";


    private final int filteredTaskListIndex;

    public TaskCompletedCommand (int filteredTaskListIndex){

    	this.filteredTaskListIndex = filteredTaskListIndex - 1;

    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMarkComplete = lastShownList.get(filteredTaskListIndex);
        Task completedTask = changeTaskCompletion(taskToMarkComplete);

        try {
            model.updateTask(filteredTaskListIndex, completedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToMarkComplete.getName()));
    }

    private static Task changeTaskCompletion (ReadOnlyTask taskToMarkComplete){

    	Name name = taskToMarkComplete.getName();
    	StartTime startTime = taskToMarkComplete.getStartTime();
    	EndTime endTime = taskToMarkComplete.getEndTime();
    	UniqueTagList tagList = taskToMarkComplete.getTags();
    	CompletionStatus updatedCompletionStatus = taskToMarkComplete.getCompletionStatus();
    	updatedCompletionStatus.swapStatus();

    	return new Task(name, startTime, endTime, updatedCompletionStatus, tagList);
    }

}
