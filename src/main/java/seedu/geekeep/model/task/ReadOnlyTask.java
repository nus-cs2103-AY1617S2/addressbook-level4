package seedu.geekeep.model.task;

import seedu.geekeep.model.tag.UniqueTagList;

//@@author A0121658E
/**
 * A read-only immutable interface for a Task in the Task Manager. Implementations should guarantee: details are
 * present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    //@@author A0139438W
    /**
     * Formats the Task as text, showing the details when it is present.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle());

        if (getEndDateTime() != null && getStartDateTime() != null) {
            builder.append(" Starting from: " + getStartDateTime() + " until " + getEndDateTime());
        } else if (getEndDateTime() != null && getStartDateTime() == null) {
            builder.append(" Due by: " + getEndDateTime().value);
        }

        if (getDescriptoin() != null) {
            builder.append(" Details: " + getDescriptoin().value);
        }

        if (!getTags().isEmpty()) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
    //@@author

    /**
     * Retrieve the DateTime representation of the ending date and time of the task
     * @return endingDateTime
     */
    DateTime getEndDateTime();

    /**
     * Retrieve the description of the task
     * @return Description of the Task
     */
    Description getDescriptoin();

    /**
     * Retrieve the DateTime representation of the starting date and time of the task
     * @return startingDateTime
     */
    DateTime getStartDateTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the
     * task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Retrieve the title of the task
     * @return Title
     */
    Title getTitle();

    //@@author A0139438W
    /**
     * Formats the date time of the Task as text in a reader friendly format.
     * @return display string of datetime
     */
    public String getTaskDisplayedDateString();

    /**
     * Formats the description of the Task as text in a reader friendly format.
     * @return display string of datetime
     */
    public String getTaskDisplayedDescriptionString();
    //@@author

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null
                        && other.isEvent()
                        && this.isEvent() // this is first to avoid NPE below
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && other.getStartDateTime().equals(this.getStartDateTime())
                        && other.getEndDateTime().equals(this.getEndDateTime())
                        && this.isDone() == other.isDone())
                || (other != null
                        && other.isDeadline()
                        && this.isDeadline()
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && other.getEndDateTime().equals(this.getEndDateTime())
                        && this.isDone() == other.isDone())
                || (other != null
                        && other.isFloatingTask()
                        && this.isFloatingTask()
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && this.isDone() == other.isDone());
    }

    //@@author A0148037E
    public int getPriority();
    //@@author

    //@@author A0148037E
    /**
     * Get the task's DateTime that is used to compare date time.
     * For events, the startDateTime is used for comparison.
     * For deadlines, the endDateTime is used for comparison.
     * @return DateTime object
     */
    DateTime getReferenceDateTime();

    /**
     * Compares this task's type priority with another.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    public int comparePriority(ReadOnlyTask otherTask);

    /**
     * Compares this task's reference datetime with another in chronological order.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    int compareDate(ReadOnlyTask otherTask);

    /**
     * Compares this task's type priority and reference datetime with another.
     * Compares this task's title with another in lexicographic order if both are floating tasks.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    public int comparePriorityAndDatetimeAndTitle(ReadOnlyTask otherTask);

    /**
     * Compares this task's title with another in lexicographic order.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    public int compareTitle(ReadOnlyTask otherTask);
    //@@author

    /**
     * Returns true if the task does not have a start datetime or end datetime
     * @return floating task identity
     */
    boolean isFloatingTask();

    /**
     * Returns true if the task has a start datetime nad an end datetime
     * @return event identity
     */
    boolean isEvent();

    /**
     * Returns true if the task has an end datetime but not a start datetime
     * @return deadline identity
     */
    boolean isDeadline();

    /**
     * Returns true if the task is marked as completed by user
     * @return status of the task
     */
    boolean isDone();
}
