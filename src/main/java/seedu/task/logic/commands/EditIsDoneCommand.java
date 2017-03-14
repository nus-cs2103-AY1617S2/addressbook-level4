package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class EditIsDoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCE = "Done Task: %1$s";

    public final int targetIndex;

    public EditIsDoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask updatedTaskDone = lastShownList.get(targetIndex - 1);
        try {
            model.IsDoneTask(targetIndex - 1, updatedTaskDone);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCE, updatedTaskDone));
    }

}
