package seedu.whatsleft.logic.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.events.ui.JumpToCalendarEventEvent;
import seedu.whatsleft.commons.events.ui.JumpToCalendarTaskEvent;
import seedu.whatsleft.commons.events.ui.JumpToEventListRequestEvent;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.CollectionUtil;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.ModelManager;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.EndDate;
import seedu.whatsleft.model.activity.EndTime;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.StartDate;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueEventList;
import seedu.whatsleft.model.activity.UniqueTaskList;
import seedu.whatsleft.model.tag.UniqueTagList;

/**
 * Edits the details of an existing activity in WhatsLeft.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    private static final String DEFAULT_BY_TIME = "2359";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the type and index number used in the last activity listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: TYPE (ev represents event and ts represents task, INDEX (must be a positive integer) "
            + "[DESCRIPTION] [p/PRIORITY] [l/LOCATION ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " ts 1 p/high bd/050517";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_EDIT_SUCCESS_CLASH = "Edited Activity with possible clash! : %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in WhatsLeft.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in WhatsLeft.";
    public static final String MESSAGE_DIFFERENT_DEADLINE = "Cannot edit Deadline into Task or Event";
    public static final String MESSAGE_DIFFERENT_TASK = "Cannot edit Task into Event or Deadline";
    public static final String MESSAGE_DIFFERENT_EVENT = "Cannot edit Event into Deadline or Task";
    public static final String MESSAGE_EDIT_CLASH_TIME = "Cannot edit Event as it clashes with another event!";
    public static final String MESSAGE_ILLEGAL_EVENT_END_DATETIME = "End Date/Time cannot be before Start Date!";

    private final int filteredActivityListIndex;
    private final EditEventDescriptor editEventDescriptor;
    private final EditTaskDescriptor editTaskDescriptor;
    private final String type;

    //@@author A0110491U
    /**
     * @param filteredActivityListIndex the index of the activity in the filtered activity list to edit
     * @param editEventDescriptor details to edit the event with
     * @param editTaskDescriptor details to edit the task with
     * @throws IllegalValueException
     */
    public EditCommand(int filteredActivityListIndex, EditEventDescriptor editEventDescriptor,
            EditTaskDescriptor editTaskDescriptor, String type) throws IllegalValueException {
        assert filteredActivityListIndex > 0;
        assert editEventDescriptor != null;
        assert editTaskDescriptor != null;
        assert type != null;

        // converts filteredActivityListIndex from one-based to zero-based.
        this.filteredActivityListIndex = filteredActivityListIndex - 1;
        this.type = type;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (type.equals("ev")) {
            return editingEvent();
        }

        if (type.equals("ts")) {
            return editingTask();
        }
        return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

    }

    //@@author A0110491U
    /**
     * This method edits the event chosen
     * @return CommandResult of the editing of an event
     * @throws CommandException if invalid index or incorrect end_date/time or duplicate event
     */
    private CommandResult editingEvent() throws CommandException {
        List<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        if (filteredActivityListIndex >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = (Event) lastShownEventList.get(filteredActivityListIndex);
        try {
            if (!isValidEndDateTime()) {
                throw new IllegalValueException(MESSAGE_ILLEGAL_EVENT_END_DATETIME);
            }
            Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);
            try {
            //store for undo operation
                ReadOnlyWhatsLeft currState = model.getWhatsLeft();
                ModelManager.setPreviousState(currState);
                model.updateEvent(eventToEdit, editedEvent);
            } catch (UniqueEventList.DuplicateEventException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_EVENT);
            }
            model.updateFilteredListToShowAll();
            model.storePreviousCommand("edit");

            UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
            EventsCenter.getInstance().post(new JumpToEventListRequestEvent(lastShownList.indexOf(editedEvent)));
            if (!editedEvent.isOver()) {
                EventsCenter.getInstance().post(new JumpToCalendarEventEvent(editedEvent));
            }
            if (model.eventHasClash(editedEvent)) {
                return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS_CLASH, editedEvent));
            }
            return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_ILLEGAL_EVENT_END_DATETIME);
        }
    }

    //@@author A0110491U
    /**
     * This method edits the task chosen
     * @return CommandResult of the editing of an task
     * @throws CommandException if invalid index or duplicate task
     */
    private CommandResult editingTask() throws CommandException {
        List<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();
        if (filteredActivityListIndex >= lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = (Task) lastShownTaskList.get(filteredActivityListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        try {
            //store for undo operation
            ReadOnlyWhatsLeft currState = model.getWhatsLeft();
            ModelManager.setPreviousState(currState);
            model.updateTask(taskToEdit, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        model.storePreviousCommand("edit");

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(lastShownList.indexOf(editedTask)));
        EventsCenter.getInstance().post(new JumpToCalendarTaskEvent(editedTask));
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(
                taskToEdit::getDescription);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        ByTime updatedByTime = editTaskDescriptor.getByTime().orElseGet(taskToEdit::getByTime);
        ByDate updatedByDate = editTaskDescriptor.getByDate().orElseGet(taskToEdit::getByDate);
        Location updatedLocation = editTaskDescriptor.getLocation().orElseGet(taskToEdit::getLocation);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        if (updatedByDate.value != null && updatedByTime.value == null) {
            try {
                updatedByTime = new ByTime(DEFAULT_BY_TIME);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }

        if (updatedByTime.value != null && updatedByDate.value == null) {
            updatedByDate = new ByDate(LocalDate.now());
        }
        return new Task(updatedDescription, updatedPriority, updatedByTime, updatedByDate,
                 updatedLocation, updatedTags, taskToEdit.getStatus());
    }

    //@@author A0110491U
    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     * @throws IllegalValueException
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                             EditEventDescriptor editEventDescriptor) throws IllegalValueException {
        assert eventToEdit != null;

        Description updatedDescription = editEventDescriptor.getDescription().orElseGet(
                eventToEdit::getDescription);
        StartTime updatedStartTime = editEventDescriptor.getStartTime().orElseGet(eventToEdit::getStartTime);
        StartDate updatedStartDate = editEventDescriptor.getStartDate().orElseGet(eventToEdit::getStartDate);
        EndTime updatedEndTime = editEventDescriptor.getEndTime().orElseGet(eventToEdit::getEndTime);
        EndDate updatedEndDate = editEventDescriptor.getEndDate().orElseGet(eventToEdit::getEndDate);
        Location updatedLocation = editEventDescriptor.getLocation().orElseGet(eventToEdit::getLocation);
        UniqueTagList updatedTags = editEventDescriptor.getTags().orElseGet(eventToEdit::getTags);

        return new Event(updatedDescription, updatedStartTime, updatedStartDate,
                updatedEndTime, updatedEndDate, updatedLocation, updatedTags);
    }

    //@@author A0110491U
    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditEventDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<StartTime> startTime = Optional.empty();
        private Optional<StartDate> startDate = Optional.empty();
        private Optional<EndTime> endTime = Optional.empty();
        private Optional<EndDate> endDate = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.startTime = toCopy.getStartTime();
            this.startDate = toCopy.getStartDate();
            this.endTime = toCopy.getEndTime();
            this.endDate = toCopy.getEndDate();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.startTime, this.endTime,
                    this.startDate, this.endDate, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setStartTime(Optional<StartTime> startTime) {
            this.startTime = startTime;
        }

        public Optional<StartTime> getStartTime() {
            return startTime;
        }

        public void setStartDate(Optional<StartDate> startDate) {
            this.startDate = startDate;
        }

        public Optional<StartDate> getStartDate() {
            return startDate;
        }

        public void setEndTime(Optional<EndTime> endTime) {
            this.endTime = endTime;
        }

        public Optional<EndTime> getEndTime() {
            return endTime;
        }

        public void setEndDate(Optional<EndDate> endDate) {
            this.endDate = endDate;
        }

        public Optional<EndDate> getEndDate() {
            return endDate;
        }

        public void setLocation(Optional<Location> location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }

    //@@author A0121668A
    /**
     * Checks if input values violates time/date constraint
     * @return true if constraint violated, no otherwise
     */
    private boolean isValidEndDateTime() {

        EndDate endDateToCompare;
        EndTime endTimeToCompare;
        StartDate startDateToCompare;
        StartTime startTimeToCompare;
        ReadOnlyEvent eventToEdit = model.getFilteredEventList().get(filteredActivityListIndex);

        if (editEventDescriptor.getEndDate().isPresent()) {
            endDateToCompare = editEventDescriptor.getEndDate().get();
        } else {
            endDateToCompare = eventToEdit.getEndDate();
        }

        if (editEventDescriptor.getStartDate().isPresent()) {
            startDateToCompare = editEventDescriptor.getStartDate().get();
        } else {
            startDateToCompare = eventToEdit.getStartDate();
        }

        if (editEventDescriptor.getEndTime().isPresent()) {
            endTimeToCompare = editEventDescriptor.getEndTime().get();
        } else {
            endTimeToCompare = eventToEdit.getEndTime();
        }

        if (editEventDescriptor.getStartTime().isPresent()) {
            startTimeToCompare = editEventDescriptor.getStartTime().get();
        } else {
            startTimeToCompare = eventToEdit.getStartTime();
        }

        return Event.isValideEndDateTime(endTimeToCompare, endDateToCompare, startTimeToCompare, startDateToCompare);
    }

    //@@author A0110491U
    /**
     * Stores the details to edit the Task with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditTaskDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<ByDate> byDate = Optional.empty();
        private Optional<ByTime> byTime = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.priority = toCopy.getPriority();
            this.byDate = toCopy.getByDate();
            this.byTime = toCopy.getByTime();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description,
                    this.byDate, this.byTime, this.priority, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setByTime(Optional<ByTime> byTime) {
            this.byTime = byTime;
        }

        public Optional<ByTime> getByTime() {
            return byTime;
        }

        public void setByDate(Optional<ByDate> byDate) {
            this.byDate = byDate;
        }

        public Optional<ByDate> getByDate() {
            return byDate;
        }

        public void setPriority(Optional<Priority> priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setLocation(Optional<Location> location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
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
