package seedu.geekeep.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the address book.
 */
public class UpdateCommand extends Command {

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<DateTime> endDateTime = Optional.empty();
        private Optional<DateTime> startDateTime = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.endDateTime = toCopy.getEndDateTime();
            this.startDateTime = toCopy.getStartDateTime();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        public Optional<DateTime> getEndDateTime() {
            return endDateTime;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public Optional<DateTime> getStartDateTime() {
            return startDateTime;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public Optional<Title> getTitle() {
            return title;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            if (this.startDateTime != null || this.endDateTime != null) {
                return true;
            }
            return CollectionUtil.isAnyPresent(this.title, this.location, this.tags);
        }

        public void setEndDateTime(Optional<DateTime> endDateTime) {
            this.endDateTime = endDateTime;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public void setStartDateTime(Optional<DateTime> startDateTime) {
            this.startDateTime = startDateTime;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public void setTitle(Optional<Title> title) {
            assert title != null;
            this.title = title;
        }
    }

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[TITLE] [s/STARTING_TIME] [e/ENDING_TIME] [l/LOCATION] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 s/01-04-17 1630 e/01-04-17 1730";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        assert taskToEdit != null;

        Title updatedTitle = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        DateTime updatedEndDateTime = null;
        if (editTaskDescriptor.getEndDateTime() != null) {
            updatedEndDateTime = editTaskDescriptor.getEndDateTime().orElseGet(taskToEdit::getEndDateTime);
        }
        DateTime updatedStartDateTime = null;
        if (editTaskDescriptor.getStartDateTime() != null) {
            updatedStartDateTime = editTaskDescriptor.getStartDateTime().orElseGet(taskToEdit::getStartDateTime);
        }
        Location updatedLocation = editTaskDescriptor.getLocation().orElseGet(taskToEdit::getLocation);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedTitle, updatedStartDateTime, updatedEndDateTime, updatedLocation, updatedTags);
    }

    private final int filteredTaskListIndex;

    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public UpdateCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
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
            model.updateTask(taskToEdit, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
}
