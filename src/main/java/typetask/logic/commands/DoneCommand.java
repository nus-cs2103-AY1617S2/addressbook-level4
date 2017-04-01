package typetask.logic.commands;

import typetask.commons.core.Messages;
import typetask.commons.core.UnmodifiableObservableList;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.TaskList.TaskNotFoundException;

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";
    public static final String COMMAND_WORD2 = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number in the task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed? Yes";

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

        //@@author A0144902L
        try {
            model.storeTaskManager(COMMAND_WORD);
            model.completeTask(targetIndex - 1, taskToComplete);
            //Need to do: Add the completed task to the completed task list
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }
}
