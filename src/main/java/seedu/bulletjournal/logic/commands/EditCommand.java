package seedu.bulletjournal.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.commons.util.CollectionUtil;
import seedu.bulletjournal.logic.commands.exceptions.CommandException;
import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.BeginDate;
import seedu.bulletjournal.model.task.DueDate;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.TaskName;
import seedu.bulletjournal.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    //CODESTYLE.OFF: RuleName
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[TASKNAME] [d/DUEDATE] [s/STATUS] [b/BEGINDATE ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 d/91234567 s/undone";
    //CODESTYLE.ON: RuleName
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task with
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

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowUndone();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of
     * {@code taskToEdit} edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        TaskName updatedName = editTaskDescriptor.getTaskName().orElseGet(taskToEdit::getTaskName);
        DueDate updatedPhone = editTaskDescriptor.getPhone().orElseGet(taskToEdit::getPhone);
        Status updatedEmail = editTaskDescriptor.getStatus().orElseGet(taskToEdit::getStatus);
        BeginDate updatedAddress = editTaskDescriptor.getAddress().orElseGet(taskToEdit::getAddress);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value
     * will replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<TaskName> taskName = Optional.empty();
        private Optional<DueDate> dueDate = Optional.empty();
        private Optional<Status> status = Optional.empty();
        private Optional<BeginDate> beginDate = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.taskName = toCopy.getTaskName();
            this.dueDate = toCopy.getPhone();
            this.status = toCopy.getStatus();
            this.beginDate = toCopy.getAddress();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.taskName, this.dueDate, this.status, this.beginDate, this.tags);
        }

        public void setName(Optional<TaskName> taskName) {
            assert taskName != null;
            this.taskName = taskName;
        }

        public Optional<TaskName> getTaskName() {
            return taskName;
        }

        public void setPhone(Optional<DueDate> dueDate) {
            assert dueDate != null;
            this.dueDate = dueDate;
        }

        public Optional<DueDate> getPhone() {
            return dueDate;
        }

        public void setEmail(Optional<Status> status) {
            assert status != null;
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return status;
        }

        public void setAddress(Optional<BeginDate> beginDate) {
            assert beginDate != null;
            this.beginDate = beginDate;
        }

        public Optional<BeginDate> getAddress() {
            return beginDate;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
