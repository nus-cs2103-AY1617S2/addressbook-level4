//@@author A0139925U
package seedu.tache.logic.commands;

import java.util.ArrayList;
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
            + "Parameters: INDEX (must be a positive integer); <parameter1> <new_value1>;"
            + "<parameter2> <new_value2>...\n"
            + "Example: " + COMMAND_WORD + " 1; start_date 10/11/2017; start_time 3.30pm;"
            + "Or alternatively you can use the following format\n"
            + "Parameters: INDEX (must be a positive integer) change <parameter1> to <new_value1> and "
            + "<parameter2> to <new_value2>...\n"
            + "Example: " + COMMAND_WORD + " 1 change startdate to 10/11/2017 and change starttime to 3.30pm;";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_INVALID_DATE_RANGE = "Start date can not be before end date";
    public static final String MESSAGE_PART_OF_RECURRING_TASK =
                        "This task is part of a recurring task and cannot be edited.";

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
            undoHistory.push(this);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskListIndex(taskToEdit)));
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage()).execute();
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalValueException if date or time could not be parsed
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
        boolean isTimed;
        if (updatedStartDateTime.isPresent() || updatedEndDateTime.isPresent()) {
            isTimed = true;
        } else {
            isTimed = false;
        }
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        updatedEndDateTime = checkFloatingToNonFloatingCase(editTaskDescriptor, updatedStartDateTime,
                                                                updatedEndDateTime);
        checkValidDateRange(updatedStartDateTime, updatedEndDateTime);
        checkSpecialCase(editTaskDescriptor, updatedEndDateTime);

        return new Task(updatedName, updatedStartDateTime, updatedEndDateTime,
                            updatedTags, isTimed, true, false, RecurInterval.NONE, new ArrayList<Date>());

    }

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

    private static void checkValidDateRange(Optional<DateTime> updatedStartDateTime,
                                                Optional<DateTime> updatedEndDateTime) throws IllegalValueException {
        if (updatedStartDateTime.isPresent() && updatedEndDateTime.isPresent()) {
            if (updatedStartDateTime.get().compareTo(updatedEndDateTime.get()) == 1) {
                throw new IllegalValueException(MESSAGE_INVALID_DATE_RANGE);
            }
        }
    }

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
               taskToEdit.getTimedStatus(), taskToEdit.getActiveStatus(), taskToEdit.getRecurState().isRecurring(),
               taskToEdit.getRecurState().getRecurInterval(), taskToEdit.getRecurState().getRecurCompletedList());
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
