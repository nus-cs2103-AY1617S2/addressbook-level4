package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurrence;
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
            + "Parameters: INDEX (must be a positive integer) [TITLE] [by DEADLINE] [#LABEL]...\n"
            + "Parameters: INDEX (must be a positive integer) [TITLE] [by DEADLINE] [from START to END][#LABEL]...\n"
            + "Example: " + COMMAND_WORD + " 1 by Sunday #new";

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
            saveCurrentState();
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Optional<Deadline> updatedStartTime;
        Optional<Deadline> updatedDeadline;
        Optional<Recurrence> updatedRecurrence;
        Title updatedTitle = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        if ((editTaskDescriptor.getClearDates().isPresent() && editTaskDescriptor.getClearDates().get() == true)
                || editTaskDescriptor.isDateEdited()) {
            updatedStartTime = editTaskDescriptor.getStartTime();
            updatedDeadline = editTaskDescriptor.getDeadline();
        } else {
            updatedStartTime = taskToEdit.getStartTime();
            updatedDeadline = taskToEdit.getDeadline();
        }
        Boolean isCompleted = editTaskDescriptor.isCompleted().orElseGet(taskToEdit::isCompleted);
        UniqueLabelList updatedLabels = editTaskDescriptor.getLabels().orElseGet(taskToEdit::getLabels);
        Boolean isRecurring;
        if (editTaskDescriptor.isRecurrenceEdited()) {
            isRecurring = editTaskDescriptor.getIsRecurring().get();
            updatedRecurrence = editTaskDescriptor.getRecurrence();
        } else {
            updatedRecurrence = taskToEdit.getRecurrence();
            isRecurring = taskToEdit.isRecurring();
        }
        UniqueBookingList updatedBookings = editTaskDescriptor.getBookings().orElseGet(taskToEdit::getBookings);
        Task newTask = new Task(updatedTitle, updatedStartTime, updatedDeadline, isCompleted, updatedLabels,
                            isRecurring, updatedRecurrence);
        newTask.setBookings(updatedBookings);
        return newTask;
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
        private Optional<Boolean> clearDates = Optional.empty();
        private Optional<Boolean> isRecurring = Optional.empty();
        private Optional<Boolean> removeRecurrence = Optional.empty();
        private Optional<Recurrence> recurrence = Optional.empty();

        private Optional<UniqueBookingList> bookings = Optional.empty();
        public EditTaskDescriptor() {}


        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.startTime = toCopy.getStartTime();
            this.deadline = toCopy.getDeadline();
            this.isCompleted = toCopy.isCompleted();
            this.labels = toCopy.getLabels();
            this.clearDates = toCopy.getClearDates();
            this.bookings = toCopy.getBookings();
            this.isRecurring = toCopy.getIsRecurring();
            this.recurrence = toCopy.getRecurrence();
            this.removeRecurrence = toCopy.getRemoveRecurrence();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.startTime,
                    this.isCompleted, this.deadline, this.labels, this.clearDates,
                    this.isRecurring, this.recurrence, this.removeRecurrence);
        }

        /**
         * Returns true if any date is edited.
         */
        public boolean isDateEdited() {
            return CollectionUtil.isAnyPresent(this.startTime, this.deadline);
        }

        public boolean isRecurrenceEdited() {
            return CollectionUtil.isAnyPresent(this.recurrence, this.isRecurring);
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

        public Optional<UniqueBookingList> getBookings() {
            return bookings;
        }

        public void setIsCompleted(Optional<Boolean> isCompleted) {
            this.isCompleted = isCompleted;
        }

        public Optional<Boolean> isCompleted() {
            return isCompleted;
        }

        public void setClearDates(Optional<Boolean> clearDates) {
            this.clearDates = clearDates;
        }

        public Optional<Boolean> getClearDates() {
            return clearDates;
        }

        public void setIsRecurring(Optional<Boolean> isRecurring) {
            this.isRecurring = isRecurring;
        }

        public Optional<Boolean> getIsRecurring() {
            return isRecurring;
        }

        public void setRemoveRecurrence(Optional<Boolean> removeRecurrence) {
            this.removeRecurrence = removeRecurrence;
        }

        public Optional<Boolean> getRemoveRecurrence() {
            return removeRecurrence;
        }

        public void setRecurrence(Optional<Recurrence> recurrence) {
            this.recurrence = recurrence;
        }

        public Optional<Recurrence> getRecurrence() {
            return recurrence;
        }

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
