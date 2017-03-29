package seedu.watodo.logic.commands;

import seedu.watodo.commons.core.Messages;
import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private int[] filteredTaskListIndices;

    public DeleteCommand(int[] args) {
        this.filteredTaskListIndices = args;

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            assert filteredTaskListIndices != null;
            assert filteredTaskListIndices.length > 0;
            assert filteredTaskListIndices[i] > 0;

            // converts filteredTaskListIndex to from one-based to zero-based.
            filteredTaskListIndices[i] = filteredTaskListIndices[i] - 1;
        }
    }


    @Override
    public CommandResult execute() throws CommandException {
        final StringBuilder tasksDeletedMessage = new StringBuilder();

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (filteredTaskListIndices[i] >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToDelete = lastShownList.get(filteredTaskListIndices[i]);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

            tasksDeletedMessage.append(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete) + "\n");
        }

        return new CommandResult(tasksDeletedMessage.toString());
    }

}
