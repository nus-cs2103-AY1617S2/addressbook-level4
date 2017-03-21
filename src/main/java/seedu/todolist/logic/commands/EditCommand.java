package seedu.todolist.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.parser.TaskParser;

/**
 * Edits the details of an existing task in the to-do list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [s/START_TIME] [e/END_TIME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/reply emails from Mary s/12/02/2017 9.00 PM e/13/02/2017 9.00 PM";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the to-do list.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;
    private String commandText;

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
        List<Task> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();

        commandText = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit,
            EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        StartTime updatedStartTime = editTaskDescriptor.getStartTime().orElseGet(taskToEdit::getStartTime);
        EndTime updatedEndTime = editTaskDescriptor.getEndTime().orElseGet(taskToEdit::getEndTime);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return TaskParser.parseTask(updatedName, updatedStartTime, updatedEndTime, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<StartTime> startTime = Optional.empty();
        private Optional<EndTime> endTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name,
                    this.startTime,
                    this.endTime,
                    this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStartTime(Optional<StartTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<StartTime> getStartTime() {
            return startTime;
        }

        public void setEndTime(Optional<EndTime> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<EndTime> getEndTime() {
            return endTime;
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
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandText() {
        return commandText;
    }
}
