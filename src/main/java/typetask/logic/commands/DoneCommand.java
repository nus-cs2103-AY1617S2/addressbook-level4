package typetask.logic.commands;

import typetask.commons.core.Messages;
import typetask.commons.core.UnmodifiableObservableList;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.TaskList.TaskNotFoundException;

//@@author A0144902L
/**
 * Marks a task as done from identifying it's displayed index from the TaskManager.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";
    public static final String COMMAND_WORD2 = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number in the task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Task Completed!";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        try {
            model.storeTaskManager(COMMAND_WORD);
            model.completeTask(targetIndex - 1, taskToComplete);
            //Need to do: Add the completed task to the completed task list
        } catch (TaskNotFoundException tnfe) {
            throw new CommandException(Messages.MESSAGE_TASK_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }
}
