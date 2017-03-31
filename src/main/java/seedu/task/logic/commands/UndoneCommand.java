package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

    //@@author A0139975J
public class UndoneCommand extends Command {

    public static final String COMMAND_WORD_1 = "undone";
    public static final String COMMAND_WORD_2 = "ud";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + ": the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD_1 + " 1";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "UnDone Task: %1$s";

    public final int targetIndex;
    //@@author A0139975J
    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    //@@author A0139975J
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask updatedTaskDone = lastShownList.get(targetIndex - 1);
        try {
            model.unDoneTask(targetIndex - 1, updatedTaskDone);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

//        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, updatedTaskDone));
    }

}
