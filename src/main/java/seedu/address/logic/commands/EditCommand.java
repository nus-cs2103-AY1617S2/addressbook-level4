package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [by DEADLINE] "
            + "[from STARTDATE] [to ENDDATE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 by the day after tomorrow";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

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

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    //@@author A0140023E
    /**
     * Creates and returns a {@link Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                         EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null && editTaskDescriptor != null;

        Name updatedName = getUpdatedName(taskToEdit, editTaskDescriptor);
        Optional<Deadline> updatedDeadline = getUpdatedDeadline(taskToEdit, editTaskDescriptor);
        Optional<StartEndDateTime> updatedStartEndDateTime =
                getUpdatedStartEndDateTime(taskToEdit, editTaskDescriptor);
        UniqueTagList updatedTagList = getUpdatedTagList(taskToEdit, editTaskDescriptor);

        return new Task(updatedName, updatedDeadline, updatedStartEndDateTime, updatedTagList);
    }

    /**
     * Returns the updated {@link Name} from {@code editTaskDescriptor} if it exists, otherwise
     * returns the original task {@link Name} from {@code taskToEdit}
     */
    private static Name getUpdatedName(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null && editTaskDescriptor != null;

        return editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
    }

    /**
     * Returns an {@link Optional} wrapping the updated {@link Deadline} from
     * {@code editTaskDescriptor} if it exists, otherwise returns the original task {@link Deadline}
     * from {@code taskToEdit}
     */
    private static Optional<Deadline> getUpdatedDeadline(ReadOnlyTask taskToEdit,
                                                         EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null && editTaskDescriptor != null;

        if (editTaskDescriptor.getDeadline().isPresent()) {
            // Wrap the deadline from editTaskDescriptor with a new Optional
            // so we do not depend on the descriptor anymore
            return Optional.of(editTaskDescriptor.getDeadline().get());
        }

        return taskToEdit.getDeadline();
    }

    /**
     * Returns an {@link Optional} wrapping the updated {@link StartEndDateTime} from
     * {@code editTaskDescriptor} if it exists, otherwise returns the original task
     * {@link StartEndDateTime} from {@code taskToEdit}
     */
    private static Optional<StartEndDateTime> getUpdatedStartEndDateTime(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null && editTaskDescriptor != null;

        if (editTaskDescriptor.getStartEndDateTime().isPresent()) {
            // Wrap the StartEndDateTime from editTaskDescriptor with a new Optional
            // so we do not depend on the descriptor anymore
            return Optional.of(editTaskDescriptor.getStartEndDateTime().get());
        }

        return taskToEdit.getStartEndDateTime();
    }

    /**
     * Returns the updated {@link UniqueTagList} from {@code editTaskDescriptor} if it exists, otherwise
     * returns the original task's {@link UniqueTagList} from {@code taskToEdit}
     */
    private static UniqueTagList getUpdatedTagList(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null && editTaskDescriptor != null;

        return editTaskDescriptor.getTagList().orElseGet(taskToEdit::getTags);
    }

    //@@author
    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<StartEndDateTime> startEndDateTime = Optional.empty();
        private Optional<UniqueTagList> tagList = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            name = toCopy.getName();
            deadline = toCopy.getDeadline();
            startEndDateTime = toCopy.getStartEndDateTime();
            tagList = toCopy.getTagList();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, deadline, startEndDateTime, tagList);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setDeadline(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return deadline;
        }

        public void setStartEndDateTime(Optional<StartEndDateTime> startEndDateTime) {
            assert startEndDateTime != null;
            this.startEndDateTime = startEndDateTime;
        }

        public Optional<StartEndDateTime> getStartEndDateTime() {
            return startEndDateTime;
        }

        public void setTagList(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tagList = tags;
        }

        public Optional<UniqueTagList> getTagList() {
            return tagList;
        }
    }
}
