package seedu.taskit.logic.commands;

//@@author A0141872E
import java.util.List;
import java.util.Optional;

import seedu.taskit.commons.core.Messages;
import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.commons.util.CollectionUtil;
import seedu.taskit.logic.commands.exceptions.CommandException;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.Title;
import seedu.taskit.model.task.UniqueTaskList;

/**
 * Edits an existing task in TaskIt.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [title|deadline|tag|priority] NEW.\n"
            + "Example: " + COMMAND_WORD + " 2 title finish SWE HW";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskIt.";

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
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
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

        Title updatedTask = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        Date updatedStart = editTaskDescriptor.getStart().orElseGet(taskToEdit::getStart);
        Date updatedEnd = editTaskDescriptor.getEnd().orElseGet(taskToEdit::getEnd);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedTask, updatedStart, updatedEnd, updatedPriority, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<Date> start = Optional.empty();
        private Optional<Date> end = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.start = toCopy.getStart();
            this.end = toCopy.getEnd();
            this.priority = toCopy.getPriority();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.start, this.end, this.priority, this.tags);
        }

        public void setTitle(Optional<Title> title) {
            assert title != null;
            this.title = title;
        }

        private Optional<Title> getTitle() {
            return title;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        // @@author A0163996J

        public void setStart(Optional<Date> start) {
            this.start = start;
        }

        private Optional<Date> getStart() {
            return start;
        }

        public void setEnd(Optional<Date> end) {
            this.end = end;
        }

        private Optional<Date> getEnd() {
            return end;
        }

        public void setPriority(Optional<Priority> priority) {
            this.priority = priority;
        }

        private Optional<Priority> getPriority() {
            return priority;
        }

        // @@ author
    }

    //@@author A0141011J
    public boolean isUndoable() {
        return true;
    }
}
