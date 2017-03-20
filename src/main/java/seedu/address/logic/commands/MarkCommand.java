package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_COMPLETED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_INCOMPLETE;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;

public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "MARK";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task as completed or incomplete, task is "
            + "identified by the index number used in the last task listing. "
            + "Parameters: INDEX [" + PREFIX_STATUS_COMPLETED + " | " + PREFIX_STATUS_INCOMPLETE + "]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_STATUS_INCOMPLETE;

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    public static final String MESSAGE_NOT_MARKED = "Status must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final Boolean isCompleted;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     *
     */
    public MarkCommand(int filteredTaskListIndex, Boolean isCompleted) {
        assert filteredTaskListIndex > 0;
        assert isCompleted != null;
        this.isCompleted = isCompleted;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }


    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, isCompleted);

        try {
            saveCurrentState();
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             Boolean isCompleted) {
        assert taskToEdit != null;

        Title updatedTitle = taskToEdit.getTitle();
        Optional<Deadline> updatedStartTime = taskToEdit.getStartTime();
        Optional<Deadline> updatedDeadline = taskToEdit.getDeadline();
        UniqueLabelList updatedLabels = taskToEdit.getLabels();

        return new Task(updatedTitle, updatedStartTime, updatedDeadline, isCompleted, updatedLabels);
    }


    /**
     * Save the data in task manager if command is mutating the data
     */
    public void saveCurrentState() {
        if (isMutating()) {
            try {
                LogicManager.undoCommandHistory.addStorageHistory(model.getTaskManager().getImmutableTaskList(),
                        model.getTaskManager().getImmutableLabelList());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

}
