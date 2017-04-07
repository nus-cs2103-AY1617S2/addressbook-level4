package seedu.task.logic.commands;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using its last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "The name of the list can also be specified.\n"
            + "Parameters: [LIST_NAME] INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " floating 1" + " or " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;
    public final String targetList;

    public DeleteCommand(String targetList, int targetIndex) {
        this.targetIndex = targetIndex - 1;
        this.targetList = targetList;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = getTargetTaskList(targetList);

        validateTargetIndex(targetIndex, lastShownList);

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
