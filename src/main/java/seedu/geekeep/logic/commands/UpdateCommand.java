package seedu.geekeep.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Description;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in GeeKeep.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[TITLE] [s/STARTING_TIME] [e/ENDING_TIME] [l/LOCATION] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 s/01-04-17 1630 e/01-04-17 1730";
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in GeeKeep.";

    private final int filteredTaskListIndex;
    private final UpdateTaskDescriptor updateTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param updateTaskDescriptor details to edit the task with
     */
    public UpdateCommand(int filteredTaskListIndex, UpdateTaskDescriptor updateTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert updateTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.updateTaskDescriptor = new UpdateTaskDescriptor(updateTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(filteredTaskListIndex);
        Task updatedTask;
        try {
            updatedTask = createEditedTask(taskToUpdate, updateTaskDescriptor);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        try {
            model.updateTask(filteredTaskListIndex, updatedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }

    //@@author A0139438W
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * updated with {@code updateTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToUpdate,
            UpdateTaskDescriptor updateTaskDescriptor) throws IllegalValueException {
        assert taskToUpdate != null;

        Title updatedTitle = updateTaskDescriptor.getTitle().orElseGet(taskToUpdate::getTitle);
        DateTime updatedEndDateTime = null;
        if (updateTaskDescriptor.getEndDateTime() != null) {
            updatedEndDateTime = updateTaskDescriptor.getEndDateTime().orElseGet(taskToUpdate::getEndDateTime);
        }
        DateTime updatedStartDateTime = null;
        if (updateTaskDescriptor.getStartDateTime() != null) {
            updatedStartDateTime = updateTaskDescriptor.getStartDateTime().orElseGet(taskToUpdate::getStartDateTime);
        }
        Description updatedLocation = updateTaskDescriptor.getLocation().orElseGet(taskToUpdate::getLocation);
        UniqueTagList updatedTags = updateTaskDescriptor.getTags().orElseGet(taskToUpdate::getTags);

        return new Task(updatedTitle, updatedStartDateTime, updatedEndDateTime, updatedLocation, updatedTags,
                taskToUpdate.isDone());
    }

    /**
     * Stores the details to update the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class UpdateTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<DateTime> endDateTime = Optional.empty();
        private Optional<DateTime> startDateTime = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public UpdateTaskDescriptor() {}

        public UpdateTaskDescriptor(UpdateTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.endDateTime = toCopy.getEndDateTime();
            this.startDateTime = toCopy.getStartDateTime();
            this.description = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        public Optional<DateTime> getEndDateTime() {
            return endDateTime;
        }

        public Optional<Description> getLocation() {
            return description;
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
            if (this.startDateTime == null || this.endDateTime == null) {
                return true;
            }
            return CollectionUtil.isAnyPresent(this.title, this.description, this.tags,
                    this.startDateTime, this.endDateTime);
        }

        public void setEndDateTime(Optional<DateTime> endDateTime) {
            this.endDateTime = endDateTime;
        }

        public void setLocation(Optional<Description> description) {
            assert description != null;
            this.description = description;
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
}
