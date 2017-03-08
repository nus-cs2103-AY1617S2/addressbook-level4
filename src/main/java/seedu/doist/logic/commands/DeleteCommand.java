package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.commons.core.Messages;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the to-do list.
 */
public class DeleteCommand extends Command {

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("delete", "del"));
    public static final String DEFAULT_COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = info().getUsageTextForCommandWords()
            + ": Deletes the tasks identified by the index numbers used in the last task listing.\n"
            + "Parameters: INDEX [INDEX...] (must be a positive integer)\n"
            + "Example: " + DEFAULT_COMMAND_WORD + " 1 8";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public final int[] targetIndices;

    public DeleteCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ArrayList<ReadOnlyTask> tasksToDelete = new ArrayList<ReadOnlyTask>();

        for (int targetIndex : targetIndices) {
            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
            tasksToDelete.add(taskToDelete);
        }

        for (ReadOnlyTask task : tasksToDelete) {
            try {
                model.deleteTask(task);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, tasksToDelete));
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
