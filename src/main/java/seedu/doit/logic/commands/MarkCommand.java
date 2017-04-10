// @@author A0139399J
package seedu.doit.logic.commands;

import seedu.doit.commons.core.Messages;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.UniqueTaskList;

public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_PARAMETER = "INDEX";
    public static final String COMMAND_RESULT = "Marks task at specified index as completed";
    public static final String COMMAND_EXAMPLE = "mark 20";
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

        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = this.model.getFilteredTaskList();

        if (this.filteredTaskListIndex <= lastShownTaskList.size()) {
            ReadOnlyTask taskToMark = lastShownTaskList.get(this.filteredTaskListIndex - 1);

            try {
                this.model.markTask(this.filteredTaskListIndex - 1, taskToMark);
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

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLE;
    }
}
