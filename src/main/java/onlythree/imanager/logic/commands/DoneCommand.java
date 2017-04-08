package onlythree.imanager.logic.commands;

import java.util.List;

import onlythree.imanager.commons.core.Messages;
import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.model.task.UniqueTaskList;

//@@author A0135998H
/**
 * Mark an existing task in the task list as "done".
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an existing task "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     */
    public DoneCommand(int filteredTaskListIndex) {
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

        Task editedTask;
        try {
            editedTask = new Task(taskToEdit);
        } catch (IllegalValueException e) {
            throw new AssertionError("Copying a valid task should always result in a valid task");
        }
        /* switch status of task */
        editedTask.setComplete(!(taskToEdit.isComplete()));

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateFilteredListToShowDone();
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToEdit));
    }

}
