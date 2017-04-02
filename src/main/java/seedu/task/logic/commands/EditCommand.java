package seedu.task.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Complete;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 t/hipri due/2017/01/01 1230 starts/2016/01/01 1230 ends/2017/01/01 1230";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";
    public static final String MESSAGE_INVALID_DURATION = "Must have start date and end date for duration.";

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
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        mementos.addUndoMementoAndClearRedo(new TaskMemento((Task) taskToEdit, (Task) editedTask));

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    //@@author A0163673Y
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws CommandException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) throws CommandException {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        DueDate updatedDueDate = editTaskDescriptor.getDueDate().orElseGet(taskToEdit::getDueDate);
        String updatedDurationStart = editTaskDescriptor.getDurationStart().orElseGet(taskToEdit::getDurationStart);
        String updatedDurationEnd = editTaskDescriptor.getDurationEnd().orElseGet(taskToEdit::getDurationEnd);
        Duration updatedDuration = taskToEdit.getDuration();
        Complete updatedComplete = taskToEdit.getComplete();
        TaskId originalId = taskToEdit.getTaskId();

        try {
            // ensure that there must be start and end date if editing a non existing duration
            if (taskToEdit.getDuration() == null && (updatedDurationStart == null ^ updatedDurationEnd == null)) {
                throw new CommandException(MESSAGE_INVALID_DURATION);
            }
            // ensure we only update duration if new start and end date are not null
            if (updatedDurationStart != null && updatedDurationEnd != null) {
                updatedDuration = new Duration(updatedDurationStart, updatedDurationEnd);
            }
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        // delete due date from task
        if (editTaskDescriptor.deleteDueDate) {
            updatedDueDate = null;
        }

        // delete duration from task
        if (editTaskDescriptor.deleteDuration) {
            updatedDuration = null;
        }

        return new Task(updatedDescription, updatedDueDate, updatedDuration, updatedTags, updatedComplete, originalId);
    }
    //@@author

    //@@author A0163673Y
    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<String> durationStart = Optional.empty();
        private Optional<String> durationEnd = Optional.empty();
        private Optional<DueDate> dueDate = Optional.empty();
        private boolean deleteDueDate = false;
        private boolean deleteDuration = false;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.tags = toCopy.getTags();
            this.durationStart = toCopy.getDurationStart();
            this.durationEnd = toCopy.getDurationEnd();
            this.dueDate = toCopy.getDueDate();
            this.deleteDueDate = toCopy.getDeleteDueDate();
            this.deleteDuration = toCopy.getDeleteDuration();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.tags,
                    this.durationStart, this.durationEnd, this.dueDate)
                    || this.deleteDueDate
                    || this.deleteDuration;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setDurationStart(Optional<String> durationStart) {
            assert durationStart != null;
            this.durationStart = durationStart;
        }

        public Optional<String> getDurationStart() {
            return durationStart;
        }

        public void setDurationEnd(Optional<String> durationEnd) {
            assert durationEnd != null;
            this.durationEnd = durationEnd;
        }

        public Optional<String> getDurationEnd() {
            return durationEnd;
        }

        public void setDueDate(Optional<DueDate> dueDate) {
            assert dueDate != null;
            this.dueDate = dueDate;
        }

        public Optional<DueDate> getDueDate() {
            return dueDate;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public boolean getDeleteDueDate() {
            return this.deleteDueDate;
        }

        public void setDeleteDueDate(boolean deleteDueDate) {
            this.deleteDueDate = deleteDueDate;
        }

        public boolean getDeleteDuration() {
            return this.deleteDuration;
        }

        public void setDeleteDuration(boolean deleteDuration) {
            this.deleteDuration = deleteDuration;
        }
    }
    //@@author
}
