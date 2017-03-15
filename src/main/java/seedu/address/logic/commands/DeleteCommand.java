package seedu.address.logic.commands;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the list
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            System.out.println("invalid index");
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            int indexRemoved = model.deleteTask(taskToDelete);
            model.getUndoStack().push(COMMAND_WORD);
            System.out.println("getUndoStack() " + COMMAND_WORD);
            model.getDeletedStackOfTasks().push(taskToDelete);
            System.out.println("getDeletedStackOfTasks() " + taskToDelete.getAsText());
            model.getDeletedStackOfTasksIndex().push(indexRemoved);
        } catch (TaskNotFoundException tnfe) {
            System.out.println("Task not found");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
