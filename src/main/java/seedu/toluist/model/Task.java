//@@author A0131125Y
package seedu.toluist.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeSet;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.StringUtil;

/**
 * Represents a Task
 */
public class Task implements Comparable<Task>, Cloneable {
    public static final String HIGH_PRIORITY_STRING = "high";
    public static final String LOW_PRIORITY_STRING = "low";
    private static final String ERROR_VALIDATION_EMPTY_DESCRIPTION = "Description must not be empty.";
    private static final String ERROR_VALIDATION_START_DATE_AFTER_END_DATE = "Start date must be before end date.";
    private static final String ERROR_VALIDATION_UNCLASSIFIED_TASK = "Task cannot contain only start date.";
    private static final String ERROR_INVALID_PRIORITY_LEVEL = "Priority level must be either 'low' or 'high'.";
    private static final String ERROR_INVALID_RECURRING_FREQUENCY = "Recurring frequency must be either 'daily',"
            + "'weekly', 'monthly' or 'yearly'.";
    private static final String ERROR_INVALID_RECURRING_END_DATE = "Non-recurring tasks cannot have end "
            + "date of recurrence,";

    //@@author A0162011A
    public static final String CATEGORY_PRIORITY = "priority";
    public static final String CATEGORY_STARTDATE = "startdate";
    public static final String CATEGORY_ENDDATE = "enddate";
    public static final String CATEGORY_OVERDUE = "overdue";
    public static final String CATEGORY_DESCRIPTION = "description";
    public static final String CATEGORY_DEFAULT = "default";
    private static final int START_OF_ARRAY_INDEX = 0;

    private static String[] defaultSortOrder = { CATEGORY_OVERDUE, CATEGORY_PRIORITY, CATEGORY_ENDDATE,
                                                 CATEGORY_STARTDATE, CATEGORY_DESCRIPTION};
    private static LinkedList<String> sortingOrder = new LinkedList<String>(Arrays.asList(defaultSortOrder));

    //@@author A0131125Y
    // List of tags is unique
    private TreeSet<Tag> allTags = new TreeSet<>();
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime completionDateTime;
    private LocalDateTime recurringEndDateTime;
    private RecurringFrequency recurringFrequency;
    private TaskPriority priority = TaskPriority.LOW;

    public enum TaskType {
        TASK, DEADLINE, EVENT
    }

    public enum TaskPriority {
        HIGH, LOW
    }

    public enum RecurringFrequency {
        DAILY, WEEKLY, MONTHLY, YEARLY
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

    //@@author A0127545A
    public Task(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.setDescription(description.trim());
        this.setStartDateTime(startDateTime);
        this.setEndDateTime(endDateTime);
        validate();
    }

    public void setTask(Task task) {
        allTags = task.allTags;
        description = task.description;
        startDateTime = task.startDateTime;
        endDateTime = task.endDateTime;
        completionDateTime = task.completionDateTime;
        recurringEndDateTime = task.recurringEndDateTime;
        recurringFrequency = task.recurringFrequency;
        priority = task.priority;
    }

    public void validate() {
        if (!validateDescriptionMustNotBeEmpty()) {
            throw new IllegalArgumentException(ERROR_VALIDATION_EMPTY_DESCRIPTION);
        }
        if (!validateStartDateMustBeBeforeEndDate()) {
            throw new IllegalArgumentException(ERROR_VALIDATION_START_DATE_AFTER_END_DATE);
        }
        if (!validateTaskIsFloatingIsEventOrHasDeadline()) {
            throw new IllegalArgumentException(ERROR_VALIDATION_UNCLASSIFIED_TASK);
        }
    }

    public boolean validateDescriptionMustNotBeEmpty() {
        return StringUtil.isPresent(description);
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

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public TaskType getTaskType() {
        if (isEvent()) {
            return TaskType.EVENT;
        } else if (isTaskWithDeadline()) {
            return TaskType.DEADLINE;
        } else {
            return TaskType.TASK;
        }
    }

    //@@author A0131125Y
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.description.equals(((Task) other).description)) // state check
                && this.priority.equals(((Task) other).priority)
                && this.allTags.equals(((Task) other).allTags)
                && Objects.equals(this.recurringFrequency, ((Task) other).recurringFrequency) // handles null
                && Objects.equals(this.startDateTime, ((Task) other).startDateTime) // handles null
                && Objects.equals(this.endDateTime, ((Task) other).endDateTime) // handles null
                && Objects.equals(this.completionDateTime, ((Task) other).completionDateTime) // handles null
                && Objects.equals(this.recurringEndDateTime, ((Task) other).recurringEndDateTime); // handles null
    }

    /**
     * Set a task as completed or incomplete
     * @param isCompleted true/false
     */
    public void setCompleted(boolean isCompleted) {
        if (isCompleted) {
            completionDateTime = LocalDateTime.now();
        } else {
            completionDateTime = null;
        }
    }

    /**
     * Set a deadline for a task
     * @param deadLine a LocalDateTime object
     */
    public void setDeadLine(LocalDateTime deadLine) {
        setStartDateTime(null);
        setEndDateTime(deadLine);
    }

    /**
     * Set a from and to date for an event
     * from should be before to
     * @param from a LocalDateTime object
     * @param to a LocalDateTime object
     */
    public void setFromTo(LocalDateTime from, LocalDateTime to) {
        assert DateTimeUtil.isBeforeOrEqual(startDateTime, endDateTime);
        setStartDateTime(from);
        setEndDateTime(to);
    }

    //@@author A0162011A
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

    //@@author A0131125Y

    /**
     * Replace the tags in the task with the given tags
     * @param tags a collection of tags
     */
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

    public boolean isRecurring() {
        return recurringFrequency != null;
    }

    //@@author A0162011A
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
                if (tag.getTagName().toLowerCase().contains(keyword.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    //@@author A0131125Y
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

    //@@author A0162011A
    @Override
    /**
     * Compare by sortingOrder
     */
    public int compareTo(Task comparison) {
        for (String currentSort : sortingOrder) {
            switch (currentSort) {
            case CATEGORY_PRIORITY :
                if (priority.compareTo(comparison.priority) != 0) {
                    return priority.compareTo(comparison.priority);
                }
                break;
            case CATEGORY_STARTDATE :
                if (!Objects.equals(startDateTime, comparison.startDateTime)) {
                    return DateTimeUtil.isBeforeOrEqual(startDateTime, comparison.startDateTime) ? -1 : 1;
                }
                break;
            case CATEGORY_ENDDATE :
                if (!Objects.equals(endDateTime, comparison.endDateTime)) {
                    return DateTimeUtil.isBeforeOrEqual(endDateTime, comparison.endDateTime) ? -1 : 1;
                }
                break;
            case CATEGORY_OVERDUE :
                if (isOverdue() != comparison.isOverdue()) {
                    return isOverdue() ? -1 : 1;
                }
                break;
            case CATEGORY_DESCRIPTION :
                if (this.description.compareToIgnoreCase(comparison.description) != 0) {
                    return this.description.compareToIgnoreCase(comparison.description);
                }
            }
        }
        return -1;
    }

    //@@author A0127545A
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

    //@@author A0131125Y
    public TaskPriority getTaskPriority() {
        return priority;
    }

    public void setTaskPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setTaskPriority(String priorityString) throws IllegalArgumentException {
        switch (priorityString.toLowerCase()) {
        case HIGH_PRIORITY_STRING:
            setTaskPriority(TaskPriority.HIGH);
            break;
        case LOW_PRIORITY_STRING:
            setTaskPriority(TaskPriority.LOW);
            break;
        default:
            throw new IllegalArgumentException(ERROR_INVALID_PRIORITY_LEVEL);
        }
    }

    public LocalDateTime getCompletionDateTime() {
        return completionDateTime;
    }

    //@@author A0127545A
    public LocalDateTime getRecurringEndDateTime() {
        return recurringEndDateTime;
    }

    public RecurringFrequency getRecurringFrequency() {
        return recurringFrequency;
    }

    public RecurringFrequency toRecurringFrequency(String recurringFrequencyString)
        throws IllegalArgumentException, NullPointerException {
        try {
            return RecurringFrequency.valueOf(recurringFrequencyString.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new IllegalArgumentException(ERROR_INVALID_RECURRING_FREQUENCY);
        }
    }

    public void setRecurringFrequency(String recurringFrequencyString) {
        setRecurringFrequency(toRecurringFrequency(recurringFrequencyString));
    }

    public void setRecurringFrequency(RecurringFrequency recurringFrequency) {
        if (isRecurring()) {
            this.recurringFrequency = recurringFrequency;
        } else {
            setRecurring(recurringFrequency);
        }
    }

    public void setRecurringEndDateTime(LocalDateTime recurringEndDateTime) throws IllegalArgumentException {
        if (isRecurring()) {
            this.recurringEndDateTime = recurringEndDateTime;
        } else {
            throw new IllegalArgumentException(ERROR_INVALID_RECURRING_END_DATE);
        }
    }

    public void setRecurring(String recurringFrequencyString) {
        setRecurring(toRecurringFrequency(recurringFrequencyString));
    }

    public void setRecurring(RecurringFrequency recurringFrequency) {
        setRecurring(null, recurringFrequency);
    }

    public void setRecurring(LocalDateTime recurringEndDateTime, String recurringFrequencyString) {
        setRecurring(recurringEndDateTime, toRecurringFrequency(recurringFrequencyString));
    }

    public void setRecurring(LocalDateTime recurringEndDateTime, RecurringFrequency recurringFrequency)
        throws IllegalArgumentException {
        if (recurringFrequency == null) {
            throw new IllegalArgumentException(ERROR_INVALID_RECURRING_FREQUENCY);
        }
        this.recurringEndDateTime = recurringEndDateTime;
        this.recurringFrequency = recurringFrequency;
    }

    public void unsetRecurring() {
        this.recurringEndDateTime = null;
        this.recurringFrequency = null;
    }

    public boolean canUpdateToNextRecurringTask() {
        if (!isRecurring()) {
            return false;
        }
        if (recurringEndDateTime == null) {
            return true;
        }
        if (endDateTime == null) {
            return LocalDateTime.now().isBefore(recurringEndDateTime);
        }
        return getNextRecurringDateTime(endDateTime).isBefore(recurringEndDateTime);
    }

    /**
     * For this recurring task, update to the next recurring task
     * Start date and end date will be updated (if they exist)
     */
    public void updateToNextRecurringTask() {
        assert isRecurring();
        if (isTaskWithDeadline()) {
            setStartDateTime(getNextRecurringDateTime(startDateTime));
            setEndDateTime(getNextRecurringDateTime(endDateTime));
        } else if (isEvent()) {
            long days = ChronoUnit.DAYS.between(endDateTime, getNextRecurringDateTime(endDateTime));
            setStartDateTime(startDateTime.plusDays(days));
            setEndDateTime(endDateTime.plusDays(days));
        }
        setCompleted(false);
    }

    public LocalDateTime getNextRecurringDateTime(LocalDateTime dateTime) {
        if (dateTime == null || this.recurringFrequency == null) {
            return null;
        }
        switch (recurringFrequency) {
        case DAILY:
            return dateTime.plusDays(1);
        case WEEKLY:
            return dateTime.plusWeeks(1);
        case MONTHLY:
            int numberOfMonths = 1;
            while (dateTime.plusMonths(numberOfMonths).getDayOfMonth() != dateTime.getDayOfMonth()) {
                numberOfMonths++;
            }
            return dateTime.plusMonths(numberOfMonths);
        case YEARLY:
            return dateTime.plusYears(1);
        default:
            return null;
        }
    }

    //@@author A0162011A
    /**
     * Changes the sorting order
     * @param keyword to sort by
     * @return true if sorting order changes / false if not
     */
    public static String[] sortBy(String keyword) {
        if (keyword.equals(CATEGORY_DEFAULT)) {
            sortingOrder = new LinkedList<String>(Arrays.asList(defaultSortOrder));
        } else {
            updateSortingOrder(keyword);
        }
        return sortingOrder.toArray(new String[sortingOrder.size()]);
    }

    public static String[] getCurrentSort() {
        return sortingOrder.toArray(new String[sortingOrder.size()]);
    }

    private static boolean updateSortingOrder(String keyword) {
        if (sortingOrder.indexOf(keyword) == START_OF_ARRAY_INDEX) {
            return false;
        }
        sortingOrder.remove(keyword);
        sortingOrder.add(START_OF_ARRAY_INDEX,  keyword);
        return true;
    }
}
