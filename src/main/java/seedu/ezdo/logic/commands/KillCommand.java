package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using its last displayed index from ezDo.
 */
public class KillCommand extends Command {

    public static final String COMMAND_WORD = "kill";
    public static final String SHORT_COMMAND_WORD = "k";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_KILL_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;

    public KillCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToKill = lastShownList.get(targetIndex - 1);

        try {
            model.killTask(taskToKill);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_KILL_TASK_SUCCESS, taskToKill));
    }

}
