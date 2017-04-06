package seedu.jobs.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.jobs.commons.core.Messages;
import seedu.jobs.commons.util.CollectionUtil;
import seedu.jobs.logic.commands.exceptions.CommandException;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.Time;
import seedu.jobs.model.task.UniqueTaskList;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

/**
 * Edits the details of an existing person in the description book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/91234567 e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the description book.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the person in the filtered person list to edit
     * @param editTaskDescriptor
     *            details to edit the person with
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
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of
     * {@code personToEdit} edited with {@code editTaskDescriptor}.
     * @throws IllegalTimeException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEditaskToEdit, EditTaskDescriptor editTaskDescriptor)
            throws IllegalTimeException {
        assert taskToEditaskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEditaskToEdit::getName);
        Time updatedStartTime = editTaskDescriptor.getStart().orElseGet(taskToEditaskToEdit::getStartTime);
        Time updatedEndTime = editTaskDescriptor.getEnd().orElseGet(taskToEditaskToEdit::getEndTime);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet
            (taskToEditaskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEditaskToEdit::getTags);
        Period updatedPeriod = editTaskDescriptor.getPeriod().orElseGet(taskToEditaskToEdit::getPeriod);
        return new Task(updatedName, updatedStartTime, updatedEndTime, updatedDescription, updatedTags, updatedPeriod);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value
     * will replace the corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Time> startTime = Optional.empty();
        private Optional<Time> endTime = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Period> period = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.startTime = toCopy.getStart();
            this.endTime = toCopy.getEnd();
            this.description = toCopy.getDescription();
            this.period = toCopy.getPeriod();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.startTime, this.endTime, this.description, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStart(Optional<Time> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<Time> getStart() {
            return startTime;
        }

        public void setEnd(Optional<Time> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<Time> getEnd() {
            return endTime;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public void setPeriod(Optional<Period> period) {
            assert period != null;
            this.period = period;
        }

        public Optional<Period> getPeriod() {
            return period;
        }
    }
}
