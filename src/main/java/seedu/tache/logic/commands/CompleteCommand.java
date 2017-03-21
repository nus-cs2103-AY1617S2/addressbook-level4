package seedu.tache.logic.commands;

import java.util.List;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Task.RecurInterval;
import seedu.tache.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes the task identified "
            + "by the index number used in the last tasks listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX1 (must be a positive integer), INDEX2, INDEX3, ... \n"
            + "Example: " + COMMAND_WORD + " 1, 2, 6, 8";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_NOT_COMPLETED = "At least one task's index must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final CompleteTaskDescriptor completeTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param completeTaskDescriptor details to edit the task with
     */
    public CompleteCommand(int filteredTaskListIndex, CompleteTaskDescriptor completeTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert completeTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.completeTaskDescriptor = new CompleteTaskDescriptor(completeTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task completedTask = createCompletedTask(taskToEdit, completeTaskDescriptor);
        try {
            model.updateTask(filteredTaskListIndex, completedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createCompletedTask(ReadOnlyTask taskToEdit,
                                             CompleteTaskDescriptor completeTaskDescriptor) {
        assert taskToEdit != null;

        boolean updatedActiveStatus = completeTaskDescriptor.getActiveStatus();

        return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                            taskToEdit.getTags(), updatedActiveStatus, false, RecurInterval.NONE);

    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class CompleteTaskDescriptor {
        private boolean isActive;

        public CompleteTaskDescriptor() {}

        public CompleteTaskDescriptor(CompleteTaskDescriptor toCopy) {
            this.isActive = toCopy.getActiveStatus();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return true;
        }

        public void setActiveStatus(boolean isActive) {
            this.isActive = isActive;
        }

        public boolean getActiveStatus() {
            return isActive;
        }
    }
}
