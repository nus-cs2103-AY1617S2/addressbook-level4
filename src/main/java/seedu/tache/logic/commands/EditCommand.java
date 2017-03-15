package seedu.tache.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.tache.commons.core.Messages;
import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Time;
import seedu.tache.model.task.UniqueDetailedTaskList;
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
    private TaskType taskType;

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

        this.taskType = TaskType.TypeTask;
    }

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     * @param taskType to differentiate between type of task
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor, TaskType taskType) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);

        this.taskType = taskType;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (taskType.equals(TaskType.TypeTask)) {
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
        } else {
            List<ReadOnlyDetailedTask> lastShownList = model.getFilteredDetailedTaskList();

            if (filteredTaskListIndex >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyDetailedTask taskToEdit = lastShownList.get(filteredTaskListIndex);
            DetailedTask editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
            try {
                model.updateDetailedTask(filteredTaskListIndex, editedTask);
            } catch (UniqueDetailedTaskList.DuplicateDetailedTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        return new Task(updatedName, updatedTags);

    }

    /**
     * Creates and returns a {@code DetailedTask} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static DetailedTask createEditedTask(ReadOnlyDetailedTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        DetailedTask temp = (DetailedTask) taskToEdit;
        Name updatedName = editTaskDescriptor.getName().orElseGet(temp::getName);
        Date updatedStartDate = editTaskDescriptor.getStartDate().orElseGet(temp::getStartDate);
        Date updateEndDate = editTaskDescriptor.getEndDate().orElseGet(temp::getEndDate);
        Time updateStartTime = editTaskDescriptor.getStartTime().orElseGet(temp::getStartTime);
        Time updateEndTime = editTaskDescriptor.getEndTime().orElseGet(temp::getEndTime);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        return new DetailedTask(updatedName, updatedStartDate, updateEndDate, updateStartTime,
                                    updateEndTime, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Date> startDate = Optional.empty();
        private Optional<Date> endDate = Optional.empty();
        private Optional<Time> startTime = Optional.empty();
        private Optional<Time> endTime = Optional.empty();
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

        public void setStartDate(Optional<Date> date) {
            assert date != null;
            this.startDate = date;
        }

        public Optional<Date> getStartDate() {
            return startDate;
        }

        public void setEndDate(Optional<Date> date) {
            assert date != null;
            this.endDate = date;
        }

        public Optional<Date> getEndDate() {
            return endDate;
        }

        public void setStartTime(Optional<Time> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<Time> getStartTime() {
            return startTime;
        }

        public void setEndTime(Optional<Time> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<Time> getEndTime() {
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
