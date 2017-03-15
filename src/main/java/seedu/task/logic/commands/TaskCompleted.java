package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;

public abstract class TaskCompleted extends Command {


	@Override
    public abstract CommandResult execute() throws CommandException;

	public Task changeTaskCompletion (ReadOnlyTask taskToMarkComplete) {

        Name name = taskToMarkComplete.getName();
        StartTime startTime = taskToMarkComplete.getStartTime();
        EndTime endTime = taskToMarkComplete.getEndTime();
        UniqueTagList tagList = taskToMarkComplete.getTags();
        CompletionStatus updatedCompletionStatus = taskToMarkComplete.getCompletionStatus();
        updatedCompletionStatus.swapStatus();

        return new Task(name, startTime, endTime, updatedCompletionStatus, tagList);
    }

}
