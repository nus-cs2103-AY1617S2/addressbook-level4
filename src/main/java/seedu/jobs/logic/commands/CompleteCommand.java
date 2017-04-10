package seedu.jobs.logic.commands;

import seedu.jobs.commons.core.Messages;
import seedu.jobs.commons.core.UnmodifiableObservableList;
import seedu.jobs.logic.commands.exceptions.CommandException;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0130979U
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": completes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the description book.";
    public final int targetIndex;

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        try {
            model.completeTask(targetIndex - 1, taskToComplete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }

}
