package seedu.toluist.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

import seedu.toluist.commons.util.DateTimeUtil;

/**
 * Represents a Task
 */
public class Task implements Comparable<Task> {
    private static final String HIGH_PRIORITY_STRING = "high";
    private static final String LOW_PRIORITY_STRING = "low";

    // List of tags is unique
    private TreeSet<Tag> allTags = new TreeSet<>();
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime completionDateTime;
    private TaskPriority priority = TaskPriority.LOW;

    public enum TaskPriority {
        HIGH, LOW
    }

    /**
     * To be used with json deserialisation
     */
    public Task() {}

    public Task(String description) {
        this(description, null, null);
    }

    public Task(String description, LocalDateTime endDateTime) {
        this(description, null, endDateTime);
    }

    public Task(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.setDescription(description.trim());
        this.setStartDateTime(startDateTime);
        this.setEndDateTime(endDateTime);
        validate();
    }

    public void validate() {
        if (!validateDescriptionMustNotBeEmpty()) {
            throw new IllegalArgumentException("Description must not be empty.");
        }
        if (!validateStartDateMustBeBeforeEndDate()) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (!validateTaskIsFloatingIsEventOrHasDeadline()) {
            throw new IllegalArgumentException("Task must be floating, must be an event, or has deadline,");
        }
    }

    public boolean validateDescriptionMustNotBeEmpty() {
        return description != null && !description.isEmpty();
    }

    public boolean validateStartDateMustBeBeforeEndDate() {
        if (startDateTime != null && endDateTime != null) {
            return startDateTime.isBefore(endDateTime);
        }
        return true;
    }

    public boolean validateTaskIsFloatingIsEventOrHasDeadline() {
        return startDateTime == null || endDateTime != null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.description.equals(((Task) other).description)) // state check
                && this.priority.equals(((Task) other).priority)
                && this.allTags.equals(((Task) other).allTags)
                && Objects.equals(this.startDateTime, ((Task) other).startDateTime) // handles null
                && Objects.equals(this.endDateTime, ((Task) other).endDateTime) // handles null
                && Objects.equals(this.completionDateTime, ((Task) other).completionDateTime); // handles null
    }

    public void setCompleted(boolean isCompleted) {
        if (isCompleted) {
            completionDateTime = LocalDateTime.now();
        } else {
            completionDateTime = null;
        }
    }

    public void setDeadLine(LocalDateTime deadLine) {
        setStartDateTime(null);
        setEndDateTime(deadLine);
    }

    public void setFromTo(LocalDateTime from, LocalDateTime to) {
        assert from.isBefore(to);
        setStartDateTime(from);
        setEndDateTime(to);
    }

    public boolean addTag(Tag tag) {
        if (allTags.contains(tag)) {
            return false;
        }

        this.allTags.add(tag);
        return true;
    }

    public boolean removeTag(Tag tag) {
        if (!allTags.contains(tag)) {
            return false;
        }

        allTags.remove(tag);
        return true;
    }

    public void replaceTags(Collection<Tag> tags) {
        this.allTags = new TreeSet<>(tags);
    }

    public TreeSet<Tag> getAllTags() {
        return allTags;
    }

    public boolean isOverdue() {
        return !isCompleted() && endDateTime != null && DateTimeUtil.isBeforeOrEqual(endDateTime, LocalDateTime.now());
    }

    public boolean isHighPriority() {
        return priority == TaskPriority.HIGH;
    }

    public boolean isFloatingTask() {
        return startDateTime == null && endDateTime == null;
    }

    public boolean isTaskWithDeadline() {
        return startDateTime == null && endDateTime != null;
    }

    public boolean isEvent() {
        return startDateTime != null && endDateTime != null;
    }

    public boolean isCompleted() {
        return completionDateTime != null && DateTimeUtil.isBeforeOrEqual(completionDateTime, LocalDateTime.now());
    }

    public boolean isAnyKeywordsContainedInDescriptionIgnoreCase(String[] keywords) {
        for (String keyword: keywords) {
            if (description.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnyKeywordsContainedInAnyTagIgnoreCase(String[] keywords) {
        for (String keyword: keywords) {
            for (Tag tag : allTags) {
                if (tag.tagName.toLowerCase().contains(keyword.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the task datetimes are within interval
     * @param from interval from
     * @param to interval to
     * @return true / false
     */
    public boolean isWithinInterval(LocalDateTime from, LocalDateTime to) {
        boolean startDateTimeWithinInterval = from == null
                || (startDateTime != null
                && DateTimeUtil.isBeforeOrEqual(from, startDateTime)
                && DateTimeUtil.isBeforeOrEqual(startDateTime, to));
        boolean endDateTimeWithinInterval = to == null
                || (endDateTime != null
                && DateTimeUtil.isBeforeOrEqual(from, endDateTime)
                && DateTimeUtil.isBeforeOrEqual(endDateTime, to));
        return startDateTimeWithinInterval || endDateTimeWithinInterval;
    }

    @Override
    /**
     * Compare by overdue first -> priority -> end date -> start date -> description
     * Floating tasks are put to the end
     */
    public int compareTo(Task comparison) {
        if (isOverdue() != comparison.isOverdue()) {
            return isOverdue() ? -1 : 1;
        } else if (priority.compareTo(comparison.priority) != 0) {
            return priority.compareTo(comparison.priority);
        } else if (!Objects.equals(endDateTime, comparison.endDateTime)) {
            return DateTimeUtil.isBeforeOrEqual(endDateTime, comparison.endDateTime) ? -1 : 1;
        } else if (!Objects.equals(startDateTime, comparison.startDateTime)) {
            return DateTimeUtil.isBeforeOrEqual(startDateTime, comparison.startDateTime) ? -1 : 1;
        } else {
            return this.description.compareToIgnoreCase(comparison.description);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public TaskPriority getTaskPriority() {
        return priority;
    }

    public void setTaskPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setTaskPriority(String priorityString) {
        if (priorityString.equalsIgnoreCase(HIGH_PRIORITY_STRING)) {
            setTaskPriority(TaskPriority.HIGH);
        } else if (priorityString.equalsIgnoreCase(LOW_PRIORITY_STRING)) {
            setTaskPriority(TaskPriority.LOW);
        } else {
            throw new IllegalArgumentException("Task priority must be either 'low' or 'high'.");
        }
    }

    public LocalDateTime getCompletionDateTime() {
        return completionDateTime;
    }
}
