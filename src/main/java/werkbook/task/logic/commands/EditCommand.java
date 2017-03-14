package werkbook.task.logic.commands;

import java.util.List;
import java.util.Optional;

import werkbook.task.commons.core.Messages;
import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.commons.util.CollectionUtil;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [d/Description]"
            + "[s/Start date and time] [e/End date and time] [t/Tag]...\n" + "Example: " + COMMAND_WORD
            + " 1 d/Walk the dog e/08/03/2017 0300";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list!";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task
     *            list to edit
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
            return new CommandResult(ive.getMessage());
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
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Description updatedDescription = editTaskDescriptor.getDescription()
                .orElseGet(taskToEdit::getDescription);
        StartDateTime updatedStartDateTime = editTaskDescriptor.getStartDateTime()
                .orElseGet(taskToEdit::getStartDateTime);
        EndDateTime updatedEndDateTime = editTaskDescriptor.getEndDateTime()
                .orElseGet(taskToEdit::getEndDateTime);
        // Must make it not override first tag
        taskToEdit.getTags().mergeFromFirst(editTaskDescriptor.getTags());

        return new Task(updatedName, updatedDescription, updatedStartDateTime, updatedEndDateTime,
                taskToEdit.getTags());
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value
     * will replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<StartDateTime> startDateTime = Optional.empty();
        private Optional<EndDateTime> endDateTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.description = toCopy.getDescription();
            this.startDateTime = toCopy.getStartDateTime();
            this.endDateTime = toCopy.getEndDateTime();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.description, this.startDateTime,
                    this.endDateTime, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setStartDateTime(Optional<StartDateTime> startDateTime) {
            assert startDateTime != null;
            this.startDateTime = startDateTime;
        }

        public Optional<StartDateTime> getStartDateTime() {
            return startDateTime;
        }

        public void setEndDateTime(Optional<EndDateTime> endDateTime) {
            assert endDateTime != null;
            this.endDateTime = endDateTime;
        }

        public Optional<EndDateTime> getEndDateTime() {
            return endDateTime;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
