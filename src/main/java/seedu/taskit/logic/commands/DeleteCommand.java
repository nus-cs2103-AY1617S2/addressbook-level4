//@@author A0141011J

package seedu.taskit.logic.commands;

import seedu.taskit.commons.core.Messages;
import seedu.taskit.commons.core.UnmodifiableObservableList;
import seedu.taskit.logic.commands.exceptions.CommandException;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Deletes a task identified using it's last displayed index from TaskIt.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    //@@author A0141011J
    @Override
    public boolean isUndoable() {
        return true;
    }
}
