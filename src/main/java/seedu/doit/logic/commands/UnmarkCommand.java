// @@author A0139399J
package seedu.doit.logic.commands;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.Messages;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.events.ui.JumpToListRequestEvent;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.UniqueTaskList;

public class UnmarkCommand extends Command {
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Changes the task identified by the index number used in the last task list to uncompleted.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    public final int filteredTaskListIndex;

    public UnmarkCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;
        this.filteredTaskListIndex = filteredTaskListIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = this.model.getFilteredTaskList();

        if (this.filteredTaskListIndex <= lastShownTaskList.size()) {
            ReadOnlyTask taskToUnmark = lastShownTaskList.get(this.filteredTaskListIndex - 1);

            try {
                this.model.unmarkTask(this.filteredTaskListIndex - 1, taskToUnmark);
            } catch (UniqueTaskList.TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            EventsCenter.getInstance().post(new JumpToListRequestEvent(
                    this.model.getFilteredTaskList().indexOf(taskToUnmark)));
            return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }
}
