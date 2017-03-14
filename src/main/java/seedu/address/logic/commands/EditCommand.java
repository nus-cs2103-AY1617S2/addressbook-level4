package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [TITLE] [by DEADLINE] [from START to END][t/LABEL]...\n"
            + "Example: " + COMMAND_WORD + " 1 by Sunday t/new";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Title updatedTitle = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        Optional<Deadline> updatedStartTime = editTaskDescriptor.getStartTime();
        Optional<Deadline> updatedDeadline = editTaskDescriptor.getDeadline();
        Boolean isCompleted = editTaskDescriptor.isCompleted().orElseGet(taskToEdit::isCompleted);
        UniqueLabelList updatedLabels = editTaskDescriptor.getLabels().orElseGet(taskToEdit::getLabels);

        return new Task(updatedTitle, updatedStartTime, updatedDeadline, isCompleted, updatedLabels);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<Deadline> startTime = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<UniqueLabelList> labels = Optional.empty();
        private Optional<Boolean> isCompleted = Optional.empty();

        public EditTaskDescriptor() {}


        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.startTime = toCopy.getStartTime();
            this.deadline = toCopy.getDeadline();
            this.isCompleted = toCopy.isCompleted();
            this.labels = toCopy.getLabels();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.startTime,
                    this.isCompleted, this.deadline, this.labels);
        }

        /**
         * Returns true if any date is edited.
         */
        public boolean isDateEdited() {
            return CollectionUtil.isAnyPresent(this.startTime, this.deadline);
        }

        public void setName(Optional<Title> title) {
            assert title != null;
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return title;
        }

        public void setStartTime(Optional<Deadline> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<Deadline> getStartTime() {
            return startTime;
        }

        public void setDeadline(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return deadline;
        }

        public void setLabels(Optional<UniqueLabelList> labels) {
            assert labels != null;
            this.labels = labels;
        }

        public Optional<UniqueLabelList> getLabels() {
            return labels;
        }

        public void setIsCompleted(Optional<Boolean> isCompleted) {
            this.isCompleted = isCompleted;
        }
        public Optional<Boolean> isCompleted() {
            return isCompleted;
        }
    }
}
