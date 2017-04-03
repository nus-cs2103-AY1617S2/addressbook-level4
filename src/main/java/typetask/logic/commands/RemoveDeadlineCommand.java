package typetask.logic.commands;

import java.util.List;

import typetask.commons.core.Messages;
import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;

//@@author A0139926R
/**
 * Remove all the date and time for a task
 */
public class RemoveDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "removedeadline";
    public static final String COMMAND_WORD_SHORT = "rd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the deadline of the task identified "
            + "by the index number used in the last task listing. "
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    private final int filteredTaskListIndex;

    public RemoveDeadlineCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit);
        model.storeTaskManager(COMMAND_WORD);
        model.updateTask(filteredTaskListIndex, editedTask);
        model.updateFilteredTaskList(false);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    private static Task createEditedTask(ReadOnlyTask taskToEdit) {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        DueDate updatedDate = taskToEdit.getDate();
        DueDate updatedEndDate = taskToEdit.getEndDate();
        try {
            updatedDate = new DueDate("");
            updatedEndDate = new DueDate("");
        } catch (IllegalValueException e) {
        }
        return new Task(updatedName, updatedDate, updatedEndDate, false);
    }
}
