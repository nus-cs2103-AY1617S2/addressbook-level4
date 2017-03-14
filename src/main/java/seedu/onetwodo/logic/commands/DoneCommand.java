package seedu.onetwodo.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks the task identified by the index number as completed.
 */
public class DoneCommand extends Command {
    
    public static final String COMMAND_WORD = "done";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";
    
    public final TaskType taskType;
    public final int targetIndex;
    
    public DoneCommand(char taskType, int targetIndex) {
        this.taskType = TaskType.getTaskTypeFromChar(taskType);
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() throws CommandException {
        filterTasksByDoneStatus();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        FilteredList<ReadOnlyTask> filtered = lastShownList.filtered(t -> t.getTaskType() == taskType);

        if (filtered.size() < targetIndex || taskType == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask taskToComplete = filtered.get(targetIndex - 1);
        int internalIndex = lastShownList.indexOf(taskToComplete);
        
        try {
            model.doneTask(internalIndex);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToComplete));
    }
}
