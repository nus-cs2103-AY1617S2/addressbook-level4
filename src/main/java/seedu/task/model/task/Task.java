package seedu.task.model.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName taskName;
    private TaskDate taskDate;
    private TaskTime taskStartTime;
    private TaskTime taskEndTime;
    private String taskDescription;
    private TaskStatus taskStatus;
    private LocalDate localDate;
    private LocalTime localTime;

    public static final String MESSAGE_INVALID_TIME = "Start time can't be after end time.";

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, TaskDate taskDate, TaskTime taskStartTime, TaskTime taskEndTime,
            String taskDescription, TaskStatus taskStatus, UniqueTagList tags) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
        this.taskDescription = taskDescription;

        this.taskStatus = taskStatus;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        if (localTime == null) {
            setCreationTime();
        }
    }

    public Task(TaskName parseTaskName, Optional<TaskDate> parseDate, Optional<TaskTime> parseTime,

            Optional<TaskTime> parseTime2, Optional<String> parseString, TaskStatus parseTaskStatus)
            throws IllegalValueException {

        this.taskName = parseTaskName;
        if (parseDate.isPresent()) {
            this.taskDate = parseDate.get();
        }
        if (parseTime.isPresent()) {
            this.taskStartTime = parseTime.get();
        }
        if (parseTime2.isPresent()) {
            if (this.taskStartTime != null && this.taskStartTime.compareTo(parseTime2.get()) < 0) {
                this.taskEndTime = parseTime2.get();
            } else {
                throw new IllegalValueException(MESSAGE_INVALID_TIME);
            }
        }
        if (parseString.isPresent()) {
            this.taskDescription = parseString.get();
        }
        this.taskStatus = new TaskStatus(TaskStatus.MESSAGE_INCOMPLETE);
        this.tags = new UniqueTagList();
        if (localTime == null) {
            setCreationTime();
        }

    }

    public Task(TaskName parseTaskName, Optional<TaskDate> parseDate, Optional<TaskTime> parseTime,
            Optional<TaskTime> parseTime2, Optional<String> parseString, Optional<TaskStatus> parseTaskStatus,
            UniqueTagList tags) throws IllegalValueException {
        this.taskName = parseTaskName;
        if (parseDate.isPresent()) {
            this.taskDate = parseDate.get();
        }
        if (parseTime.isPresent()) {
            this.taskStartTime = parseTime.get();
        }
        if (parseTime2.isPresent()) {
            if (this.taskStartTime != null && this.taskStartTime.compareTo(parseTime2.get()) < 0) {
                this.taskEndTime = parseTime2.get();
            } else {
                throw new IllegalValueException(MESSAGE_INVALID_TIME);
            }
        }
        if (parseString.isPresent()) {
            this.taskDescription = parseString.get();
        }
        if (parseTaskStatus.isPresent()) {
            this.taskStatus = parseTaskStatus.get();
        }
        this.tags = tags;
        if (localTime == null) {
            setCreationTime();
        }
    }

    public Task(TaskName taskName, TaskDate taskDate, TaskTime taskStartTime, TaskTime taskEndTime,
            String taskDescription, TaskStatus taskStatus) {
        this(taskName, taskDate, taskStartTime, taskEndTime, taskDescription, taskStatus, new UniqueTagList());

    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getTaskDate(), source.getTaskStartTime(), source.getTaskEndTime(),
                source.getTaskDescription(), source.getTaskStatus(), source.getTags());
        this.setLocalDate(source.getLocalDate());
        this.setLocalTime(source.getLocalTime());
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        try {
            this.setTaskName(replacement.getTaskName());
            this.setTaskDate(replacement.getTaskDate());
            this.setTaskStartTime(replacement.getTaskStartTime());
            this.setTaskEndTime(replacement.getTaskEndTime());
            this.setTaskDescription(replacement.getTaskDescription());
            this.setTaskStatus(replacement.getTaskStatus());
            this.setTags(replacement.getTags());

        } catch (IllegalValueException ive) {
            System.out.println("error resetting data in read only task"); // phrase
            // better
            // for
            // message
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(taskName, taskDate, taskStartTime, taskEndTime, taskDescription, taskStatus, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public TaskDate getTaskDate() {
        return taskDate;
    }

    public TaskTime getTaskStartTime() {
        return taskStartTime;
    }

    public TaskTime getTaskEndTime() {
        return taskEndTime;
    }

    // @@author A0146757R
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    // @@author
    public String getTaskDescription() {
        return taskDescription;
    }

    // @@author A0146757R
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    // @@author
    public void setTaskDescription(String description) {
        this.taskDescription = description;
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setTaskDate(TaskDate taskDate) {
        this.taskDate = taskDate;
    }

    public void setTaskStartTime(TaskTime taskStartTime) throws IllegalValueException {
        if (taskStartTime == null) {

        } else if (this.taskEndTime == null || this.taskEndTime.compareTo(taskStartTime) >= 0) {
            this.taskStartTime = taskStartTime;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIME);
        }
    }

    public void setTaskEndTime(TaskTime taskEndTime) throws IllegalValueException {
        if (taskEndTime == null) {

        } else if (this.taskStartTime == null || taskEndTime.compareTo(taskStartTime) >= 0) {
            this.taskEndTime = taskEndTime;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIME);
        }
    }

    // @@author A0163845X
    public void setCreationTime() {
        localDate = LocalDate.now();
        localTime = LocalTime.now();
    }

    // @@author A0163845X
    public LocalDate getLocalDate() {
        if (localDate == null) {
            setCreationTime();
        }
        return localDate;
    }

    // @@author A0163845X
    public LocalTime getLocalTime() {
        if (localTime == null) {
            setCreationTime();
        }
        return localTime;
    }

    // @@author A0163845X
    private void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    // @@author A0163845X
    private void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }
}
