// @@author A0139399J
package seedu.doit.logic.commands;

import seedu.doit.commons.core.Messages;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.UniqueTaskList;

public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Marks the task identified by the index number used in the last task list as completed.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    public final int filteredTaskListIndex;

    public MarkCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;
        this.filteredTaskListIndex = filteredTaskListIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        if (filteredTaskListIndex <= lastShownTaskList.size()) {
            ReadOnlyTask taskToMark = lastShownTaskList.get(filteredTaskListIndex - 1);

            try {
                model.markTask(filteredTaskListIndex - 1, taskToMark);
            } catch (UniqueTaskList.TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }

            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }
}
