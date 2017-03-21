package seedu.tache.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.tache.commons.core.Messages;
import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Task.RecurInterval;
import seedu.tache.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public enum TaskType { TypeTask, TypeDetailedTask };

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last tasks listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer); <parameter1> <new_value1>;"
            + "<parameter2> <new_value2>...\n"
            + "Example: " + COMMAND_WORD + " 1; start_date 10/11/2017; start_time 3.30pm;";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

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

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Optional<DateTime> updatedStartDateTime = taskToEdit.getStartDateTime();
        Optional<DateTime> updatedEndDateTime = taskToEdit.getEndDateTime();
        if (editTaskDescriptor.getStartDate().isPresent()) {
            String timeNoChange = "";
            if (updatedStartDateTime.isPresent()) {
                timeNoChange = updatedStartDateTime.get().getTimeOnly();
            }
            DateTime tempStartDateTime = new DateTime(editTaskDescriptor.getStartDate().orElse("") + timeNoChange);
            updatedStartDateTime = Optional.of(tempStartDateTime);
        }
        if (editTaskDescriptor.getEndDate().isPresent()) {
            String timeNoChange = "";
            if (updatedEndDateTime.isPresent()) {
                timeNoChange = updatedEndDateTime.get().getTimeOnly();
            }
            DateTime tempEndDateTime = new DateTime(editTaskDescriptor.getEndDate().orElse("") + timeNoChange);
            updatedEndDateTime = Optional.of(tempEndDateTime);
        }
        if (editTaskDescriptor.getStartTime().isPresent()) {
            String dateNoChange = "";
            if (updatedStartDateTime.isPresent()) {
                dateNoChange = updatedStartDateTime.get().getDateOnly();
            }
            DateTime tempStartDateTime = new DateTime(dateNoChange + editTaskDescriptor.getStartTime().orElse(""));
            updatedStartDateTime = Optional.of(tempStartDateTime);
        }
        if (editTaskDescriptor.getEndTime().isPresent()) {
            String dateNoChange = "";
            if (updatedEndDateTime.isPresent()) {
                dateNoChange = updatedEndDateTime.get().getDateOnly();
            }
            DateTime tempEndDateTime = new DateTime(dateNoChange + editTaskDescriptor.getEndTime().orElse(""));
            updatedEndDateTime = Optional.of(tempEndDateTime);
        }
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        return new Task(updatedName, updatedStartDateTime, updatedEndDateTime, updatedTags, false, RecurInterval.NONE);

    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<String> startDate = Optional.empty();
        private Optional<String> endDate = Optional.empty();
        private Optional<String> startTime = Optional.empty();
        private Optional<String> endTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.startDate = toCopy.getStartDate();
            this.endDate = toCopy.getEndDate();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.startDate, this.endDate,
                                               this.startTime, this.endTime, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStartDate(Optional<String> date) {
            assert date != null;
            this.startDate = date;
        }

        public Optional<String> getStartDate() {
            return startDate;
        }

        public void setEndDate(Optional<String> date) {
            assert date != null;
            this.endDate = date;
        }

        public Optional<String> getEndDate() {
            return endDate;
        }

        public void setStartTime(Optional<String> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<String> getStartTime() {
            return startTime;
        }

        public void setEndTime(Optional<String> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<String> getEndTime() {
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
}
