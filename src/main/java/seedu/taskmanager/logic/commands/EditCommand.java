package seedu.taskmanager.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Repeat;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Status;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String ALTERNATIVE_COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[TITLE] [s/STARTDATE] [e/ENDDATE] [d/DESCRIPTION ] [r/REPEAT] [#TAG]...\n" + "Example: " + COMMAND_WORD
            + " or " + ALTERNATIVE_COMMAND_WORD + " 1 s/23/05/2017 d/Go to John's house instead";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    // @@author A0140032E
    public static final String MESSAGE_DATE_ORDER_CONSTRAINTS = "Start Date should be earlier or same as End Date";
    public static final String MESSAGE_REPEAT_WITH_START_DATE_CONSTRAINTS = "Recurring tasks should have a start date";
    public static final String MESSAGE_REPEAT_WITH_DONE_CONSTRAINTS = "Completed tasks cannot have any repeat patterns";
    // @@author
    private final int filteredSelectedTaskListIndex;
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
        this.filteredSelectedTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getSelectedTaskList();

        if (filteredSelectedTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredSelectedTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        // @@author A0140032E
        if (editedTask.getStartDate().isPresent() && editedTask.getEndDate().isPresent()
                && editedTask.getStartDate().get().after(editedTask.getEndDate().get())) {
            throw new CommandException(MESSAGE_DATE_ORDER_CONSTRAINTS);
        }

        if (editedTask.getRepeat().isPresent() && !editedTask.getStartDate().isPresent()) {
            throw new CommandException(MESSAGE_REPEAT_WITH_START_DATE_CONSTRAINTS);
        }

        if (editedTask.getRepeat().isPresent() && editedTask.getStatus().value) {
            throw new CommandException(MESSAGE_REPEAT_WITH_DONE_CONSTRAINTS);
        }
        // @@author
        try {
            model.updateTask(filteredSelectedTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Title updatedTitle = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        // @@author A0140032E
        Optional<StartDate> updatedStartDate = editTaskDescriptor.isStartDateChanged()
                ? editTaskDescriptor.getStartDate() : taskToEdit.getStartDate();
        Optional<EndDate> updatedEndDate = editTaskDescriptor.isEndDateChanged() ? editTaskDescriptor.getEndDate()
                : taskToEdit.getEndDate();
        Optional<Description> updatedDescription = editTaskDescriptor.isDescriptionChanged()
                ? editTaskDescriptor.getDescription() : taskToEdit.getDescription();
        Optional<Repeat> updatedRepeat = editTaskDescriptor.isRepeatChanged() ? editTaskDescriptor.getRepeat()
                : taskToEdit.getRepeat();
        // @@author
        Status updatedStatus = editTaskDescriptor.getStatus().orElseGet(taskToEdit::getStatus);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedTitle, updatedStartDate, updatedEndDate, updatedDescription, updatedRepeat,
                updatedStatus, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<StartDate> startDate = Optional.empty();
        private Optional<EndDate> endDate = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<Repeat> repeat = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Status> status = Optional.empty();
        // @@author A0140032E
        private boolean anyChangesMade;
        private boolean startDateChanged;
        private boolean endDateChanged;
        private boolean descriptionChanged;
        private boolean repeatChanged;

        public EditTaskDescriptor() {
            anyChangesMade = false;
            startDateChanged = false;
            endDateChanged = false;
            descriptionChanged = false;
            repeatChanged = false;
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.startDate = toCopy.getStartDate();
            this.endDate = toCopy.getEndDate();
            this.description = toCopy.getDescription();
            this.repeat = toCopy.getRepeat();
            this.status = toCopy.getStatus();
            this.tags = toCopy.getTags();
            this.anyChangesMade = toCopy.isAnyFieldEdited();
            this.startDateChanged = toCopy.isStartDateChanged();
            this.endDateChanged = toCopy.isEndDateChanged();
            this.descriptionChanged = toCopy.isDescriptionChanged();
            this.repeatChanged = toCopy.isRepeatChanged();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return anyChangesMade;
        }

        public void setTitle(Optional<Title> title) {
            assert title != null;
            this.title = title;
            anyChangesMade = true;
        }
        // @@author

        public Optional<Title> getTitle() {
            return title;
        }

        public Optional<Status> getStatus() {
            return status;
        }

        // @@author A0140032E
        public void setStartDate(Optional<StartDate> startDate) {
            assert startDate != null;
            this.startDate = startDate;
            startDateChanged = true;
            anyChangesMade = true;
        }

        public boolean isStartDateChanged() {
            return startDateChanged;
        }
        // @@author

        public Optional<StartDate> getStartDate() {
            return startDate;
        }

        // @@author A0140032E
        public void setEndDate(Optional<EndDate> endDate) {
            assert endDate != null;
            this.endDate = endDate;
            anyChangesMade = true;
            endDateChanged = true;
        }

        public boolean isEndDateChanged() {
            return endDateChanged;
        }
        // @@author

        public Optional<EndDate> getEndDate() {
            return endDate;
        }

        // @@author A0140032E
        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
            anyChangesMade = true;
            descriptionChanged = true;
        }

        public boolean isDescriptionChanged() {
            return descriptionChanged;
        }
        // @@author

        public Optional<Description> getDescription() {
            return description;
        }

        // @@author A0140032E
        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
            anyChangesMade = true;
        }
        // @@author

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        // @@author A0140032E
        public void setRepeat(Optional<Repeat> repeat) {
            assert repeat != null;
            this.repeat = repeat;
            anyChangesMade = true;
            repeatChanged = true;
        }

        public boolean isRepeatChanged() {
            return repeatChanged;
        }

        public Optional<Repeat> getRepeat() {
            return repeat;
        }

        // @@author
    }
}
