package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Event;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueEventList;
import seedu.address.model.person.UniqueTaskList;

import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing activity in WhatsLeft.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the type and index number used in the last activity listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: TYPE (ev represents event and ts represents task, INDEX (must be a positive integer) "
            + "[DESCRIPTION] [p/PRIORITY] [l/LOCATION ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + "ts 1 p/high bd/050517";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in WhatsLeft.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in WhatsLeft.";
    public static final String MESSAGE_DIFFERENT_DEADLINE = "Cannot edit Deadline into Task or Event";
    public static final String MESSAGE_DIFFERENT_TASK = "Cannot edit Task into Event or Deadline";
    public static final String MESSAGE_DIFFERENT_EVENT = "Cannot edit Event into Deadline or Task";

    private final int filteredActivityListIndex;
    private final EditEventDescriptor editEventDescriptor;
    private final EditTaskDescriptor editTaskDescriptor;
    private final String type;
    //@@author A0110491U
    /**
     * @param filteredActivityListIndex the index of the activity in the filtered activity list to edit
     * @param editEventDescriptor details to edit the event with
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredActivityListIndex, EditEventDescriptor editEventDescriptor,
            EditTaskDescriptor editTaskDescriptor, String type) {
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
        List<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        List<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();
        if (type.equals("ev")) {
            if (filteredActivityListIndex >= lastShownEventList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
            }

            ReadOnlyEvent eventToEdit = lastShownEventList.get(filteredActivityListIndex);
            Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);
            try {
                model.updateEvent(filteredActivityListIndex, editedEvent);
            } catch (UniqueEventList.DuplicateEventException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_EVENT);
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, eventToEdit));
        }

        if (type.equals("ts")) {
            if (filteredActivityListIndex >= lastShownTaskList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToEdit = lastShownTaskList.get(filteredActivityListIndex);
            Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
            try {
                model.updateTask(filteredActivityListIndex, editedTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, taskToEdit));
        }

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
        EndTime updatedByTime = editTaskDescriptor.getByTime().orElseGet(taskToEdit::getByTime);
        ByDate updatedByDate = editTaskDescriptor.getByDate().orElseGet(taskToEdit::getByDate);
        Location updatedLocation = editTaskDescriptor.getLocation().orElseGet(taskToEdit::getLocation);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedDescription, updatedPriority, updatedByDate,
                updatedByTime, updatedLocation, updatedTags);
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                             EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Description updatedDescription = editEventDescriptor.getDescription().orElseGet(
            eventToEdit::getDescription);
        StartTime updatedStartTime = editEventDescriptor.getStartTime().orElseGet(eventToEdit::getStartTime);
        StartDate updatedStartDate = editEventDescriptor.getStartDate().orElseGet(eventToEdit::getStartDate);
        EndTime updatedEndTime = editEventDescriptor.getEndTime().orElseGet(eventToEdit::getEndTime);
        EndDate updatedEndDate = editEventDescriptor.getEndDate().orElseGet(eventToEdit::getEndDate);
        Location updatedLocation = editEventDescriptor.getLocation().orElseGet(eventToEdit::getLocation);
        UniqueTagList updatedTags = editEventDescriptor.getTags().orElseGet(eventToEdit::getTags);

        return new Event(updatedDescription, updatedStartDate, updatedEndDate,
                updatedStartTime, updatedEndTime, updatedLocation, updatedTags);
    }
    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditEventDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<StartTime> starttime = Optional.empty();
        private Optional<StartDate> startdate = Optional.empty();
        private Optional<EndTime> endtime = Optional.empty();
        private Optional<EndDate> enddate = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.starttime = toCopy.getStartTime();
            this.startdate = toCopy.getStartDate();
            this.endtime = toCopy.getEndTime();
            this.enddate = toCopy.getToDate();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.starttime, this.endtime,
                    this.startdate, this.enddate, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setStartTime(Optional<StartTime> starttime) {
            this.starttime = starttime;
        }

        public Optional<StartTime> getStartTime() {
            return starttime;
        }

        public void setStartDate(Optional<StartDate> startdate) {
            this.startdate = startdate;
        }

        public Optional<StartDate> getStartDate() {
            return startdate;
        }

        public void setEndTime(Optional<EndTime> endtime) {
            this.endtime = endtime;
        }

        public Optional<EndTime> getEndTime() {
            return endtime;
        }

        public void setEndDate(Optional<EndDate> enddate) {
            this.enddate = enddate;
        }

        public Optional<EndDate> getEndDate() {
            return enddate;
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

    /**
     * Stores the details to edit the Task with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditTaskDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<ByDate> bydate = Optional.empty();
        private Optional<ByTime> bytime = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.priority = toCopy.getPriority();
            this.bydate = toCopy.getByDate();
            this.bytime = toCopy.getByTime();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description,
                    this.bydate, this.bytime, this.priority, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setByTime(Optional<ByTime> bytime) {
            this.bytime = bytime;
        }

        public Optional<ByTime> getByTime() {
            return bytime;
        }

        public void setByDate(Optional<ByDate> bydate) {
            this.bydate = bydate;
        }

        public Optional<ByDate> getByDate() {
            return bydate;
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
