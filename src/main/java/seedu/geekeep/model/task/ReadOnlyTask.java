package seedu.geekeep.model.task;

import seedu.geekeep.model.tag.UniqueTagList;

//@@author A0121658E
/**
 * A read-only immutable interface for a Task in the Task Manager. Implementations should guarantee: details are
 * present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    /**
     * Formats the Task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle()).append(" Ending DateTime: ").append(getEndDateTime())
               .append(" Starting DateTime: ").append(getStartDateTime())
               .append(" Description: ").append(getDescriptoin()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

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

    DateTime getReferenceDateTime();

    public int comparePriority(ReadOnlyTask otherTask);

    int compareDate(ReadOnlyTask otherTask);

    public int comparePriorityAndDatetimeAndTitle(ReadOnlyTask otherTask);

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
