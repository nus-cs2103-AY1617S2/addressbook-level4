//@@author A0139925U
package seedu.tache.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.core.Messages;
import seedu.tache.commons.events.ui.JumpToListRequestEvent;
import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.recurstate.RecurState.RecurInterval;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "edit";
    public static final String SHORT_COMMAND_WORD = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last tasks listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) change <parameter1> to <new_value1> and "
            + "<parameter2> to <new_value2>...\n"
            + "Example: " + COMMAND_WORD + " 1 change startdate to 10 nov and change starttime to 3.30pm";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: \n%1$s";
    public static final String MESSAGE_NOT_EDITED = "No valid parameter detected to edit.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_INVALID_DATE_RANGE = "Start date can not be before end date";
    public static final String MESSAGE_PART_OF_RECURRING_TASK =
                        "This task is part of a recurring task and cannot be edited.";
    public static final String MESSAGE_REQUIRE_BOTH_START_END = "Recurring tasks requires both start date and end date";

    public static final String SPECIAL_CASE_TIME_STRING = "23:59:59";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    private boolean commandSuccess;
    private ReadOnlyTask taskToEdit;
    private ReadOnlyTask originalTask;

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
        commandSuccess = false;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(filteredTaskListIndex);
        cloneOriginalTask(taskToEdit);
        Task editedTask;
        try {
            checkPartOfRecurringTask(taskToEdit);
            editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
            try {
                model.updateTask(taskToEdit, editedTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            //model.updateCurrentFilteredList();
            commandSuccess = true;
            UndoHistory.getInstance().push(this);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskListIndex(taskToEdit)));
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage()).execute();
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @param {@code taskToEdit}, task that needs editing
     * @param {@code editTaskDescriptor}, edit details
     * @throws IllegalValueException if date or time could not be parsed
     * or if user is trying to edit a non-recurring task into a recurring task without both start date and end date
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Optional<DateTime> updatedStartDateTime;
        Optional<DateTime> updatedEndDateTime;
        if (taskToEdit.getStartDateTime().isPresent()) {
            updatedStartDateTime = Optional.of(new DateTime(taskToEdit.getStartDateTime().get()));
        } else {
            updatedStartDateTime = Optional.empty();
        }
        if (taskToEdit.getEndDateTime().isPresent()) {
            updatedEndDateTime = Optional.of(new DateTime(taskToEdit.getEndDateTime().get()));
        } else {
            updatedEndDateTime = Optional.empty();
        }
        if (editTaskDescriptor.getStartDate().isPresent()) {
            if (updatedStartDateTime.isPresent()) {
                updatedStartDateTime.get().setDateOnly(editTaskDescriptor.getStartDate().get());
            } else {
                updatedStartDateTime = Optional.of(new DateTime(editTaskDescriptor.getStartDate().get()));
                updatedStartDateTime.get().setDefaultTime();
            }
        }
        if (editTaskDescriptor.getEndDate().isPresent()) {
            if (updatedEndDateTime.isPresent()) {
                updatedEndDateTime.get().setDateOnly(editTaskDescriptor.getEndDate().get());
            } else {
                updatedEndDateTime = Optional.of(new DateTime(editTaskDescriptor.getEndDate().get()));
                updatedEndDateTime.get().setDefaultTime();
            }
        }
        if (editTaskDescriptor.getStartTime().isPresent()) {
            if (updatedStartDateTime.isPresent()) {
                updatedStartDateTime.get().setTimeOnly(editTaskDescriptor.getStartTime().get());
            } else {
                updatedStartDateTime = Optional.of(new DateTime(editTaskDescriptor.getStartTime().get()));
            }
        }
        if (editTaskDescriptor.getEndTime().isPresent()) {
            if (updatedEndDateTime.isPresent()) {
                updatedEndDateTime.get().setTimeOnly(editTaskDescriptor.getEndTime().get());
            } else {
                updatedEndDateTime = Optional.of(new DateTime(editTaskDescriptor.getEndTime().get()));
            }
        }

        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        RecurInterval updatedRecurInterval = taskToEdit.getRecurState().getRecurInterval();
        if (editTaskDescriptor.getRecurringInterval().isPresent()) {
            if (updatedStartDateTime.isPresent() && updatedEndDateTime.isPresent()) {
                updatedRecurInterval = editTaskDescriptor.getRecurringInterval().get();
            } else {
                throw new IllegalValueException(MESSAGE_REQUIRE_BOTH_START_END);
            }
        }

        updatedEndDateTime = checkFloatingToNonFloatingCase(editTaskDescriptor, updatedStartDateTime,
                                                                updatedEndDateTime);
        checkValidDateRange(updatedStartDateTime, updatedEndDateTime);
        checkSpecialCase(editTaskDescriptor, updatedEndDateTime);

        return new Task(updatedName, updatedStartDateTime, updatedEndDateTime,
                            updatedTags, true, updatedRecurInterval,
                            taskToEdit.getRecurState().getRecurCompletedList());

    }

    /**
     * Check if edit causes a floating task to become non-floating with only a start date and no end date
     * @param {@code editTaskDescriptor}, edit details
     * @param {@code updatedEndDateTime}, the final end date that will be in the new edited task
     * @param {@code updatedStartDateTime}, the final start date that will be in the new edited task
     * @throws IllegalValueException if date or time could not be parsed
     * or if user is trying to edit a non-recurring task into a recurring task without both start date and end date
     */
    private static Optional<DateTime> checkFloatingToNonFloatingCase(EditTaskDescriptor editTaskDescriptor,
                                Optional<DateTime> updatedStartDateTime, Optional<DateTime> updatedEndDateTime)
                                throws IllegalValueException {
        if (!updatedEndDateTime.isPresent() && !editTaskDescriptor.getEndDate().isPresent()) {
            if (updatedStartDateTime.isPresent()) {
                //Floating Task to Non-Floating, do not allow start date only
                Optional<DateTime> temp = Optional.of(updatedStartDateTime.get());
                return temp;
            }
        }
        return updatedEndDateTime;
    }

    /**
     * Check if start date < end date
     * @param {@code updatedEndDateTime}, the final end date that will be in the new edited task
     * @param {@code updatedStartDateTime}, the final start date that will be in the new edited task
     * @throws IllegalValueException if start date > end date
     */
    private static void checkValidDateRange(Optional<DateTime> updatedStartDateTime,
                                                Optional<DateTime> updatedEndDateTime) throws IllegalValueException {
        if (updatedStartDateTime.isPresent() && updatedEndDateTime.isPresent()) {
            if (updatedStartDateTime.get().compareTo(updatedEndDateTime.get()) == 1) {
                throw new IllegalValueException(MESSAGE_INVALID_DATE_RANGE);
            }
        }
    }

    /**
     * If overdue task date's end date is changed to today and end date < now,
     * then end time would be overwritten to 2359hrs
     * @param {@code updatedEndDateTime}, the final end date that will be in the new edited task
     * @param {@code editTaskDescriptor}, edit details
     * @throws IllegalValueException if date or time could not be parsed
     */
    private static void checkSpecialCase(EditTaskDescriptor editTaskDescriptor,
                            Optional<DateTime> updatedEndDateTime) throws IllegalValueException {
        //Special case End Date -> Today will result in a default timing of 2359 instead of 0000
        if (editTaskDescriptor.getEndDate().isPresent() && updatedEndDateTime.isPresent()
                && !editTaskDescriptor.getEndTime().isPresent()) {
            if ((new DateTime(editTaskDescriptor.getEndDate().get()).isToday())
                        && updatedEndDateTime.get().getDate().before(new Date())) {
                updatedEndDateTime.get().setTimeOnly(SPECIAL_CASE_TIME_STRING);
            }
        }
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
        private Optional<RecurInterval> interval = Optional.empty();
        private Optional<Boolean> recurringStatus = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.startDate = toCopy.getStartDate();
            this.endDate = toCopy.getEndDate();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
            this.tags = toCopy.getTags();
            this.interval = toCopy.getRecurringInterval();
            this.recurringStatus = toCopy.getRecurringStatus();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.startDate, this.endDate,
                                               this.startTime, this.endTime, this.tags,
                                               this.interval, this.recurringStatus);
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

        public void setRecurringInterval(Optional<RecurInterval> interval) {
            assert interval != null;
            this.interval = interval;
        }

        public Optional<RecurInterval> getRecurringInterval() {
            return interval;
        }

        public void setRecurringStatus(Optional<Boolean> recurringStatus) {
            assert recurringStatus != null;
            this.recurringStatus = recurringStatus;
        }

        public Optional<Boolean> getRecurringStatus() {
            return recurringStatus;
        }
    }

    private void cloneOriginalTask(ReadOnlyTask taskToEdit) {
        //Workaround as Java could not deep copy taskToEdit for some fields
        DateTime workAroundStartDateTime = null;
        DateTime workAroundEndDateTime = null;
        try {
            if (taskToEdit.getStartDateTime().isPresent()) {
                workAroundStartDateTime = new DateTime(taskToEdit.getStartDateTime().get().getAmericanDateTime());
            }
            if (taskToEdit.getEndDateTime().isPresent()) {
                workAroundEndDateTime = new DateTime(taskToEdit.getEndDateTime().get().getAmericanDateTime());
            }
        } catch (IllegalValueException e1) {
            e1.printStackTrace();
        }
        originalTask = new Task(taskToEdit.getName(), Optional.ofNullable(workAroundStartDateTime),
                                        Optional.ofNullable(workAroundEndDateTime), taskToEdit.getTags(),
                                        taskToEdit.getActiveStatus(),
                                        taskToEdit.getRecurState().getRecurInterval(),
                                        taskToEdit.getRecurState().getRecurCompletedList());
    }

    private void checkPartOfRecurringTask(ReadOnlyTask taskToEdit) throws IllegalValueException {
        if (taskToEdit.getRecurState().isGhostRecurring()) {
            throw new IllegalValueException(MESSAGE_PART_OF_RECURRING_TASK);
        }
    }

    //@@author A0150120H
    @Override
    public boolean isUndoable() {
        return commandSuccess;
    }

    @Override
    public String undo() throws CommandException {
        try {
            model.updateTask(taskToEdit, originalTask);
            model.updateFilteredListToShowAll();
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskListIndex(taskToEdit)));
            return String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }
    //@@author
}
