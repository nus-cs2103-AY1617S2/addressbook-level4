package seedu.tasklist.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.logic.commands.exceptions.CommandException;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [TASK NAME] [d/DATES] "
            + "[c/COMMENT] [p/PRIORITY] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 c/new comment here";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";
    public static final String MESSAGE_ADD_DATE_FLOATING = "Dates cannot be edited in Floating Task.";
    public static final String MESSAGE_ADD_DATE_DEADLINE = "Only one date can be edited in Deadline Task.";
    public static final String MESSAGE_ADD_DATE_EVENT = "Two dates must be edited in Event Task.";

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
        Task editedTask;
        try {
            editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Comment updatedComment = editTaskDescriptor.getComment().orElseGet(taskToEdit::getComment);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        Status updatedStatus = taskToEdit.getStatus();

        String type = taskToEdit.getType();
        switch (type) {
        case FloatingTask.TYPE:
            if (editTaskDescriptor.getDeadline().isPresent() ||
                editTaskDescriptor.getStartDate().isPresent() ||
                editTaskDescriptor.getEndDate().isPresent()) {
                throw new IllegalValueException(MESSAGE_ADD_DATE_FLOATING);
            }
            return new FloatingTask(updatedName, updatedComment, updatedPriority, updatedStatus, updatedTags);
        case DeadlineTask.TYPE:
            if (editTaskDescriptor.getStartDate().isPresent() || editTaskDescriptor.getEndDate().isPresent()) {
                throw new IllegalValueException(MESSAGE_ADD_DATE_DEADLINE);
            }
            ReadOnlyDeadlineTask deadlineTaskToEdit = (ReadOnlyDeadlineTask) taskToEdit;
            Date updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(deadlineTaskToEdit::getDeadline);
            return new DeadlineTask(updatedName, updatedComment, updatedPriority,
                                    updatedStatus, updatedDeadline, updatedTags);
        case EventTask.TYPE:
            if (editTaskDescriptor.getDeadline().isPresent()) {
                throw new IllegalValueException(MESSAGE_ADD_DATE_EVENT);
            }
            ReadOnlyEventTask eventTaskToEdit = (ReadOnlyEventTask) taskToEdit;
            Date updatedStartDate = editTaskDescriptor.getStartDate().orElseGet(eventTaskToEdit::getStartDate);
            Date updatedEndDate = editTaskDescriptor.getEndDate().orElseGet(eventTaskToEdit::getEndDate);
            return new EventTask(updatedName, updatedComment, updatedPriority,
                                 updatedStatus, updatedStartDate, updatedEndDate, updatedTags);
        default:
            return null;
        }
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Comment> comment = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<Date> deadline = Optional.empty();
        private Optional<Date> startDate = Optional.empty();
        private Optional<Date> endDate = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.comment = toCopy.getComment();
            this.tags = toCopy.getTags();
            this.priority = toCopy.getPriority();
            this.deadline = toCopy.getDeadline();
            this.startDate = toCopy.getStartDate();
            this.endDate = toCopy.getEndDate();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name,
                                               this.comment,
                                               this.tags,
                                               this.priority,
                                               this.deadline,
                                               this.startDate,
                                               this.endDate);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setComment(Optional<Comment> comment) {
            assert comment != null;
            this.comment = comment;
        }

        public Optional<Comment> getComment() {
            return comment;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public Optional<Date> getDeadline() {
            return deadline;
        }

        public void setDeadline(Optional<List<Date>> deadline) throws IllegalArgumentException {
            try {
                List<Date> dateList = deadline.get();
                if (dateList.size() == 1) {
                    this.deadline = Optional.of(deadline.get().get(0));
                } else {
                    throw new NoSuchElementException();
                }
            } catch (NoSuchElementException nse) {
                this.startDate = Optional.empty();
            }
        }

        public Optional<Date> getStartDate() {
            return startDate;
        }

        public void setStartDate(Optional<List<Date>> startDate) {
            try {
                List<Date> dateList = startDate.get();
                if (dateList.size() == 2) {
                    this.startDate = Optional.of(startDate.get().get(0));
                } else {
                    throw new NoSuchElementException();
                }
            } catch (NoSuchElementException nse) {
                this.startDate = Optional.empty();
            }
        }

        public Optional<Date> getEndDate() {
            return endDate;
        }

        public void setEndDate(Optional<List<Date>> endDate) {
            try {
                List<Date> dateList = endDate.get();
                if (dateList.size() == 2) {
                    this.endDate = Optional.of(endDate.get().get(1));
                } else {
                    throw new NoSuchElementException();
                }
            } catch (NoSuchElementException nse) {
                this.endDate = Optional.empty();
            }
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setPriority(Optional<Priority> priority) {
            this.priority = priority;
        }
    }
}
