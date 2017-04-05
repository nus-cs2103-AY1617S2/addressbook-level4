package seedu.watodo.logic.commands;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import seedu.watodo.commons.core.Messages;
import seedu.watodo.commons.util.CollectionUtil;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.watodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [TASK_DESCRIPTION] [by/ DATETIME] "
            + "[from/ START_DATETIME to/ END_DATETIME] [on/ DATETIME] [REMOVEDATES] [#TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 by/ today 3pm #urgent";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;
    private final boolean hasEditDate;
    private final boolean hasRemoveDate;

    private Task oldTask;
    private Task newTask;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor,
                       boolean hasEditDate, boolean hasRemoveDate) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
        this.hasEditDate = hasEditDate;
        this.hasRemoveDate = hasRemoveDate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        this.oldTask = new Task(taskToEdit);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor, hasEditDate, hasRemoveDate);
        this.newTask = new Task(editedTask);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    @Override
    public void unexecute() {
        try {
            model.updateFilteredListToShowAll();
            model.deleteTask(newTask);
            model.addTask(oldTask);
        } catch (TaskNotFoundException e) {

        } catch (DuplicateTaskException e) {

        }
    }

    @Override
    public void redo() {
        try {
            model.updateFilteredListToShowAll();
            model.deleteTask(oldTask);
            model.addTask(newTask);
        } catch (TaskNotFoundException e) {

        } catch (DuplicateTaskException e) {

        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor,
                                         boolean hasEditDate, boolean hasRemoveDate) {
        assert taskToEdit != null;

        Description updatedName = editTaskDescriptor.getTaskName().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        TaskStatus updatedStatus = editTaskDescriptor.getStatus().orElseGet(taskToEdit::getStatus);

        UniqueTagList existingTags = taskToEdit.getTags();
        for (Iterator<Tag> iterator = updatedTags.iterator(); iterator.hasNext();) {
            Tag tags = iterator.next();
            if (existingTags.contains(tags)) {
                iterator.remove();
                existingTags.remove(tags);
            }
        }
        updatedTags.mergeFrom(existingTags);
        if (hasRemoveDate) {
            return new Task(updatedName, null, null, updatedTags, updatedStatus);
        }
        DateTime startDate;
        DateTime endDate;
        if (!hasEditDate) {
            startDate = taskToEdit.getStartDate();
            endDate = taskToEdit.getEndDate();
        } else {
            startDate = editTaskDescriptor.getStartDate().orElse(null);
            endDate = editTaskDescriptor.getEndDate().get();
        }
        return new Task(updatedName, startDate, endDate, updatedTags, updatedStatus);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<DateTime> startDate = Optional.empty();
        private Optional<DateTime> endDate = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<TaskStatus> status = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.description = toCopy.getTaskName();
            this.startDate = toCopy.getStartDate();
            this.endDate = toCopy.getEndDate();
            this.tags = toCopy.getTags();
            this.status = toCopy.getStatus();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.startDate, this.endDate, this.tags, this.status);
        }

        public void setTaskName(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getTaskName() {
            return description;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public Optional<DateTime> getStartDate() {
            return startDate;
        }

        public void setStartDate(Optional<DateTime> startDate) {
            this.startDate = startDate;
        }

        public Optional<DateTime> getEndDate() {
            return endDate;
        }

        public void setEndDate(Optional<DateTime> endDate) {
            this.endDate = endDate;
        }

        public Optional<TaskStatus> getStatus() {
            return status;
        }

        public void setStatus(Optional<TaskStatus> status) {
            this.status = status;
        }
    }
}
