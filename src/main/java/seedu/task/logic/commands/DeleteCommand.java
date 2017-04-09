package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) OR Task name\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " fishing";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";
    public static final String MESSAGE_DELETE_TASK_FAILURE = "Task does not exist!";

    private Integer targetIndex = null;
    private String targetName = null;

    public DeleteCommand(String token) {
        try {
            this.targetIndex = Integer.parseUnsignedInt(token);
        } catch (NumberFormatException e) {
            this.targetName = token;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex != null && lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = null;
        if (targetIndex != null) taskToDelete = lastShownList.get(targetIndex - 1);
        if (targetName != null) taskToDelete = getTaskByName(lastShownList, targetName);

        try {
            model.deleteTask(taskToDelete);
        } catch (Exception pnfe) {
            assert false : "The target task cannot be missing";
        }

        if (taskToDelete != null) {
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        } else {
            throw new CommandException(MESSAGE_DELETE_TASK_FAILURE);
        }
    }

    private ReadOnlyTask getTaskByName(UnmodifiableObservableList<ReadOnlyTask> list, String name) {
        for (ReadOnlyTask task : list) {
            if (task.getName().toString().equals(name)) {
                return task;
            }
        }
        return null;
    }

}
