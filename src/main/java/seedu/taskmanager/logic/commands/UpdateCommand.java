package seedu.taskmanager.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.commons.util.DateTimeUtil;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.UniqueTaskList;

// @@author A0142418L
/**
 * Updates the details of an existing task in the task manager.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "UPDATE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [TASK] ON [DATE] FROM [STARTTIME] TO [ENDTIME]\n"
            + "Example: " + COMMAND_WORD + " 1 ON 04/03/17 FROM 1630 TO 1830";

    public static final String MESSAGE_BLOCKED_OUT_TIME = "This task cannot be added as time clashes with another event";
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_INVALID_EVENT_PERIOD = "Invalid input of time, start time has to be earlier"
            + " than end time.";

    public static final String EMPTY_FIELD = "EMPTY_FIELD";

    private final int filteredTaskListIndex;
    private UpdateTaskDescriptor updateTaskDescriptor;
    private final Boolean isUpdateToDeadlineTask;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to update
     * @param updateTaskDescriptor
     *            details to update the task with
     */
    public UpdateCommand(int filteredTaskListIndex, UpdateTaskDescriptor updateTaskDescriptor,
            Boolean isUpdateToDeadlineTask) {
        assert filteredTaskListIndex > 0;
        assert updateTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.updateTaskDescriptor = new UpdateTaskDescriptor(updateTaskDescriptor);

        this.isUpdateToDeadlineTask = isUpdateToDeadlineTask;
    }

    @Override
    public CommandResult execute() throws CommandException {
        // UpdateTaskDescriptor newUpdateTaskDescriptor = new
        // UpdateTaskDescriptor();

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(filteredTaskListIndex);

        if (!isUpdateToDeadlineTask) {
            if ((isOnlyStartUpdated() || isOnlyEndUpdated()) && taskToUpdate.isFloatingTask()) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            } else {
                if (isOnlyStartUpdated()) {
                    updateTaskDescriptor.setEndDate(Optional.of(taskToUpdate.getEndDate()));
                    updateTaskDescriptor.setEndTime(Optional.of(taskToUpdate.getEndTime()));
                } else {
                    if (isOnlyEndUpdated()) {
                        updateTaskDescriptor.setStartDate(Optional.of(taskToUpdate.getStartDate()));
                        updateTaskDescriptor.setStartTime(Optional.of(taskToUpdate.getStartTime()));
                    }
                }
            }
            if ((isOnlyStartTimeUpdated() || isOnlyEndTimeUpdated() || isOnlyTimeUpdated())
                    && (taskToUpdate.isFloatingTask() || taskToUpdate.isDeadlineTask())) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            } else {
                if (isOnlyEndTimeUpdated()) {
                    updateTaskDescriptor.setStartTime(Optional.of(taskToUpdate.getStartTime()));
                    updateTaskDescriptor.setStartDate(Optional.of(taskToUpdate.getStartDate()));
                    updateTaskDescriptor.setEndDate(Optional.of(taskToUpdate.getEndDate()));
                } else {
                    if (isOnlyStartTimeUpdated()) {
                        updateTaskDescriptor.setEndTime(Optional.of(taskToUpdate.getEndTime()));
                        updateTaskDescriptor.setStartDate(Optional.of(taskToUpdate.getStartDate()));
                        updateTaskDescriptor.setEndDate(Optional.of(taskToUpdate.getEndDate()));
                    } else {
                        if (isOnlyTimeUpdated()) {
                            updateTaskDescriptor.setStartDate(Optional.of(taskToUpdate.getStartDate()));
                            updateTaskDescriptor.setEndDate(Optional.of(taskToUpdate.getEndDate()));
                        }
                    }
                }
            }
        }

        if (isOnlyCategoriesUpdate() || isOnlyTaskNameUpdated()) {
            updateTaskDescriptor.setStartDate(Optional.of(taskToUpdate.getStartDate()));
            updateTaskDescriptor.setStartTime(Optional.of(taskToUpdate.getStartTime()));
            updateTaskDescriptor.setEndDate(Optional.of(taskToUpdate.getEndDate()));
            updateTaskDescriptor.setEndTime(Optional.of(taskToUpdate.getEndTime()));
        }

        Task updatedTask = createUpdatedTask(taskToUpdate, updateTaskDescriptor);

        try {
            if (updatedTask.isEventTask() && !DateTimeUtil.isValidEventTimePeriod(updatedTask.getStartDate().value,
                    updatedTask.getStartTime().value, updatedTask.getEndDate().value, updatedTask.getEndTime().value)) {
                throw new CommandException(MESSAGE_INVALID_EVENT_PERIOD);
            }
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_EVENT_PERIOD);
        }

        try {
            if (updatedTask.isEventTask()) {
                int clashedTaskIndex = model.isBlockedOutTime(updatedTask, filteredTaskListIndex);
                if (clashedTaskIndex != -1) {
                    String clashFeedback = "Clash with task: Index " + Integer.toString(clashedTaskIndex) + "\n";
                    model.updateTask(filteredTaskListIndex, updatedTask);
                    model.updateFilteredListToShowAll();
                    return new CommandResult(clashFeedback + String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
                }
            }
            model.updateTask(filteredTaskListIndex, updatedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }

    /**
     * Checks if only the task name field has been identified by user to be
     * updated To ensure that other task details like startTime startDate
     * endTime endDate are not lost
     *
     * @return true if only task name has been identified by user to be updated
     */
    private boolean isOnlyTaskNameUpdated() {
        if (updateTaskDescriptor.getStartDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getStartTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getTaskName().isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if only the time field has been identified by user to be
     * updated To ensure that user provides all the information that is required to update a task.
     *
     * For instance, user is only allowed to update only startTime and endTime if the task is an event
     * and not a deadline or floating task.
     *
     * @return true if only task name has been identified by user to be updated
     */
    private boolean isOnlyTimeUpdated() {
        if (updateTaskDescriptor.getStartDate().get().toString().equals(EMPTY_FIELD)
                && !updateTaskDescriptor.getStartTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndDate().get().toString().equals(EMPTY_FIELD)
                && !updateTaskDescriptor.getEndTime().get().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if only the category field has been identified by user to be
     * updated To ensure that other task details like startTime startDate
     * endTime endDate are not lost
     *
     * @return true if only categories are identified by user to be updated
     */
    private boolean isOnlyCategoriesUpdate() {
        if (updateTaskDescriptor.getStartDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getStartTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getCategories().isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if only the startTime has been identified by user to be updated To
     * ensure that other task details like startDate endTime endDate are not
     * lost
     *
     * @return true if only startTime information has been identified by user to
     *         be updated
     */
    private boolean isOnlyStartTimeUpdated() {
        if (updateTaskDescriptor.getStartDate().get().toString().equals(EMPTY_FIELD)
                && !updateTaskDescriptor.getStartTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndTime().get().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if only the endTime has been identified by user to be updated To
     * ensure that other task details like startTime startDate endDate are not
     * lost
     *
     * @return true if only endTime information has been identified by user to
     *         be updated
     */
    private boolean isOnlyEndTimeUpdated() {
        if (updateTaskDescriptor.getStartDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getStartTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndDate().get().toString().equals(EMPTY_FIELD)
                && !updateTaskDescriptor.getEndTime().get().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if only the startDate and startTime has been identified by user to
     * be updated To ensure that other task details like endDate endTime are not
     * lost
     *
     * @return true if only startDate and startTime information has been
     *         identified by user to be updated
     */
    private boolean isOnlyStartUpdated() {
        if (!updateTaskDescriptor.getStartDate().get().toString().equals(EMPTY_FIELD)
                && !updateTaskDescriptor.getStartTime().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getEndTime().get().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if only the endDate and endTime has been identified by user to be
     * updated To ensure that other task details like startDate startTime are
     * not lost
     *
     * @return true if only endDate and endTime information has been identified
     *         by user to be updated
     */
    private boolean isOnlyEndUpdated() {
        if (updateTaskDescriptor.getStartDate().get().toString().equals(EMPTY_FIELD)
                && updateTaskDescriptor.getStartTime().get().toString().equals(EMPTY_FIELD)
                && !updateTaskDescriptor.getEndDate().get().toString().equals(EMPTY_FIELD)
                && !updateTaskDescriptor.getEndTime().get().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createUpdatedTask(ReadOnlyTask taskToUpdate, UpdateTaskDescriptor updateTaskDescriptor) {
        assert taskToUpdate != null;

        TaskName updatedTaskName = updateTaskDescriptor.getTaskName().orElseGet(taskToUpdate::getTaskName);
        StartDate updatedStartDate = updateTaskDescriptor.getStartDate().orElseGet(taskToUpdate::getStartDate);
        StartTime updatedStartTime = updateTaskDescriptor.getStartTime().orElseGet(taskToUpdate::getStartTime);
        EndDate updatedEndDate = updateTaskDescriptor.getEndDate().orElseGet(taskToUpdate::getEndDate);
        EndTime updatedEndTime = updateTaskDescriptor.getEndTime().orElseGet(taskToUpdate::getEndTime);
        UniqueCategoryList updatedCategories = updateTaskDescriptor.getCategories()
                .orElseGet(taskToUpdate::getCategories);

        return new Task(updatedTaskName, updatedStartDate, updatedStartTime, updatedEndDate, updatedEndTime, false,
                updatedCategories);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class UpdateTaskDescriptor {
        private Optional<TaskName> taskname = Optional.empty();
        private Optional<StartDate> startDate = Optional.empty();
        private Optional<StartTime> startTime = Optional.empty();
        private Optional<EndDate> endDate = Optional.empty();
        private Optional<EndTime> endTime = Optional.empty();
        private Optional<UniqueCategoryList> categories = Optional.empty();

        public UpdateTaskDescriptor() {
        }

        public UpdateTaskDescriptor(UpdateTaskDescriptor toCopy) {
            this.taskname = toCopy.getTaskName();
            this.startDate = toCopy.getStartDate();
            this.startTime = toCopy.getStartTime();
            this.endDate = toCopy.getEndDate();
            this.endTime = toCopy.getEndTime();
            this.categories = toCopy.getCategories();
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyPresent(this.taskname, this.startDate, this.startTime, this.endDate,
                    this.endTime, this.categories);
        }

        public void setTaskName(Optional<TaskName> taskname) {
            assert taskname != null;
            this.taskname = taskname;
        }

        public Optional<TaskName> getTaskName() {
            return taskname;
        }

        public void setStartDate(Optional<StartDate> startDate) {
            assert startDate != null;
            this.startDate = startDate;
        }

        public Optional<StartDate> getStartDate() {
            return startDate;
        }

        public void setStartTime(Optional<StartTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<StartTime> getStartTime() {
            return startTime;
        }

        public void setEndDate(Optional<EndDate> endDate) {
            assert endDate != null;
            this.endDate = endDate;
        }

        public Optional<EndDate> getEndDate() {
            return endDate;
        }

        public void setEndTime(Optional<EndTime> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<EndTime> getEndTime() {
            return endTime;
        }

        public void setCategories(Optional<UniqueCategoryList> categories) {
            assert categories != null;
            this.categories = categories;
        }

        public Optional<UniqueCategoryList> getCategories() {
            return categories;
        }

    }
}
