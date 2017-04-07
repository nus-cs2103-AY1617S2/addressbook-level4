package seedu.doist.logic.commands;

import java.util.ArrayList;

import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0147980U
/**
 * Deletes a task identified using it's last displayed index from the to-do list.
 */
public class DeleteCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ": Deletes the tasks identified by the index numbers used in the last task listing.\n"
            + "Parameters: INDEX [INDEX...] (must be a positive integer)\n"
            + "Example: " + DEFAULT_COMMAND_WORD + " 1 8";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int[] targetIndices;

    public DeleteCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ArrayList<ReadOnlyTask> tasksToDelete = getMultipleTasksFromIndices(targetIndices);
        for (ReadOnlyTask task : tasksToDelete) {
            try {
                model.deleteTask(task);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToDelete), true);
    }
    //@@author
}
