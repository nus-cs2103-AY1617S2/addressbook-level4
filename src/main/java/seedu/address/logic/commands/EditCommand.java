package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

//@@author A0144422R
/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [due DEADLINE] [tag TAGS]...\n"
            + "Example: " + COMMAND_WORD + " 1 CS2103 Finish Tutorial";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Task edited successfully.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex,
            EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        assert filteredTaskListIndex < lastShownList.size();

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask;
        if (editTaskDescriptor.isAnyFieldEdited()) {
            try {
                editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
            } catch (IllegalValueException e) {
                throw new CommandException(e.getMessage());
            }

            try {
                model.updateTask(filteredTaskListIndex, editedTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.updateFilteredListToShowAll();
        }
        return new CommandResult(
                String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit),
                MESSAGE_SUCCESS_STATUS_BAR);
    }

    // @@author A0093999Y
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     *
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName()
                .orElseGet(taskToEdit::getName);
        UniqueTagList updatedTags = editTaskDescriptor.getTags()
                .orElseGet(taskToEdit::getTags);
        Optional<DateTime> updatedStartingTime;
        Optional<DateTime> updatedDeadline;

        if (editTaskDescriptor.getDeadline().isPresent()) {
            updatedStartingTime = editTaskDescriptor.getStartingTime();
            updatedDeadline = editTaskDescriptor.getDeadline();
        } else {
            updatedStartingTime = taskToEdit.getStartingTime();
            updatedDeadline = editTaskDescriptor.getDeadline();
        }

        return Task.createTask(updatedName, updatedTags, updatedDeadline,
                updatedStartingTime, taskToEdit.isDone(),
                taskToEdit.isManualToday());
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<DateTime> deadline = Optional.empty();
        private Optional<DateTime> startingTime = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.tags = toCopy.getTags();
            this.deadline = toCopy.getDeadline();
            this.startingTime = toCopy.getStartingTime();
        }

        // @@author
        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.tags,
                    this.deadline, this.startingTime);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        // @@author A0144422R
        public void setStartingTime(Optional<DateTime> startingTime) {
            this.startingTime = startingTime;
        }

        public Optional<DateTime> getStartingTime() {
            return this.startingTime;
        }

        public void setDeadline(Optional<DateTime> deadline) {
            this.deadline = deadline;
        }

        public Optional<DateTime> getDeadline() {
            return this.deadline;
        }
    }
}
