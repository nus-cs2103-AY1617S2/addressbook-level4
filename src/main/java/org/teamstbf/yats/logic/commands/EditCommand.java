package org.teamstbf.yats.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Recurrence;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.UniqueTagList;

//@@author A0116219L
/**
 * Edits the details of an existing task in the task scheduler.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [, START to END TIME] [//DESCRIPTION] [#TAGS]...\n"
            + "Example: " + COMMAND_WORD + " 1 s/10:00am,10/10/2017 e/5:00pm,10/10/2017 d/lots of work to do t/school";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_ILLEGAL_EDIT_RECURRING_TASK = "Date, time, periodicity of recurring "
            + "task cannot be edited.";
    public static final String MESSAGE_ILLEGAL_EDIT_RECURRENCE = "Recurrence/periodicity is not editable.";

    public static final String MESSAGE_ILLEGAL_DEADLINE_AND_EVENT_OBJECT = "Object Cannot Have Both a Deadline and "
            + "a Start and End Time";

    protected final int filteredTaskListIndex;
    protected final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task
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
        List<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyEvent taskToEdit = lastShownList.get(filteredTaskListIndex);
        Event editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        model.updateEvent(filteredTaskListIndex, editedTask);
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     *
     * @throws IllegalValueException
     */
    protected static Event createEditedTask(ReadOnlyEvent taskToEdit, EditTaskDescriptor editTaskDescriptor)
            throws CommandException {
        assert taskToEdit != null;

        if (taskToEdit.isRecurring()) {
            if (editTaskDescriptor.getStartTime().isPresent() || editTaskDescriptor.getEndTime().isPresent()
                    || editTaskDescriptor.getDeadline().isPresent()) {
                throw new CommandException(MESSAGE_ILLEGAL_EDIT_RECURRING_TASK);
            }
        }

        if (editTaskDescriptor.getRecurrence().isPresent()) {
            throw new CommandException(MESSAGE_ILLEGAL_EDIT_RECURRENCE);
        }

        Title updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getTitle);
        Location updatedLocation = editTaskDescriptor.getLocation().orElseGet(taskToEdit::getLocation);
        Schedule updatedStartTime = editTaskDescriptor.getStartTime().orElseGet(taskToEdit::getStartTime);
        Schedule updatedEndTime = editTaskDescriptor.getEndTime().orElseGet(taskToEdit::getEndTime);
        Schedule updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDeadline);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        if (editTaskDescriptor.tags.isPresent() && updatedTags.isTagPresent()) {
            updatedTags.removeAndMerge(taskToEdit.getTags());
        }

        IsDone isDone = taskToEdit.getIsDone();
        // these fields are not editable therefore unchanged
        boolean isRecurring = taskToEdit.isRecurring();
        Recurrence recurrence = taskToEdit.getRecurrence();

        if ((editTaskDescriptor.getStartTime().isPresent() && editTaskDescriptor.getDeadline().isPresent())
                || (editTaskDescriptor.getEndTime().isPresent() && editTaskDescriptor.getDeadline().isPresent())) {
            throw new CommandException(MESSAGE_ILLEGAL_DEADLINE_AND_EVENT_OBJECT);
        }

        if (editTaskDescriptor.getStartTime().isPresent() || editTaskDescriptor.getEndTime().isPresent()) {
            updatedDeadline = new Schedule("");
        }

        if (editTaskDescriptor.getDeadline().isPresent()) {
            updatedStartTime = new Schedule("");
            updatedEndTime = new Schedule("");
        }
        System.out.println(
                updatedStartTime.toString() + "||" + updatedEndTime.toString() + "||" + updatedDeadline.toString());
        return new Event(updatedName, updatedLocation, updatedStartTime, updatedEndTime, updatedDeadline,
                updatedDescription, updatedTags, isDone, isRecurring, recurrence);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> name = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<Schedule> deadline = Optional.empty();
        private Optional<Schedule> startTime = Optional.empty();
        private Optional<Schedule> endTime = Optional.empty();
        private Optional<Description> description = Optional.empty();
        Optional<UniqueTagList> tags = Optional.empty();
        private Optional<String> recurrence = Optional.empty();
        private IsDone isDone = new IsDone();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.location = toCopy.getLocation();
            this.deadline = toCopy.getDeadline();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
            this.recurrence = toCopy.getRecurrence();
            this.description = toCopy.getDescription();
            this.tags = toCopy.getTags();
            this.isDone = toCopy.getIsDone();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.location, this.startTime, this.endTime, this.deadline,
                    this.description, this.tags, this.recurrence);
        }

        public void setName(Optional<Title> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Title> getName() {
            return name;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public void setStartTime(Optional<Date> dateTime) {
            if (dateTime.isPresent()) {
                this.startTime = Optional.of(new Schedule(dateTime.get()));
            } else {
                this.startTime = Optional.empty();
            }
        }

        public void setEndTime(Optional<Date> dateTime) {
            if (dateTime.isPresent()) {
                this.endTime = Optional.of(new Schedule(dateTime.get()));
            } else {
                this.endTime = Optional.empty();
            }
        }

        public void setDeadline(Optional<Date> dateTime) {
            if (dateTime.isPresent()) {
                this.deadline = Optional.of(new Schedule(dateTime.get()));
            } else {
                this.deadline = Optional.empty();
            }
        }

        public void setRecurrence(Optional<String> periodicity) {
            if (periodicity.isPresent()) {
                // this should not happen!
                this.recurrence = Optional.of(MESSAGE_ILLEGAL_EDIT_RECURRENCE);
            }
        }

        public Optional<Schedule> getStartTime() {
            return startTime;
        }

        public Optional<Schedule> getEndTime() {
            return endTime;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public Optional<Schedule> getDeadline() {
            return deadline;
        }

        public Optional<String> getRecurrence() {
            return this.recurrence;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public IsDone getIsDone() {
            return isDone;
        }

        public IsDone markDone() {
            isDone.markDone();
            return isDone;
        }
    }

}
