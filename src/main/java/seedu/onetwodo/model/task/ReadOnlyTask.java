package seedu.onetwodo.model.task;

import seedu.onetwodo.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the toDo list. Implementations
 * should guarantee: details are present and not null, field values are
 * validated.
 */
public interface ReadOnlyTask {

    Name getName();

    StartDate getStartDate();

    EndDate getEndDate();

    Recurring getRecur();

    Priority getPriority();

    Description getDescription();

    TaskType getTaskType();

    boolean getDoneStatus();

    boolean getTodayStatus();

    boolean isOverdue();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    default boolean hasStartDate() {
        return getStartDate().hasDate();
    }

    default boolean hasEndDate() {
        return getEndDate().hasDate();
    }

    //@@author A0139343E
    default boolean hasRecur() {
        return getRecur().hasRecur();
    }

    //@@author A0141138N
    default boolean hasPriority() {
        return getPriority().hasPriority();
    }

    //@@author
    default boolean hasDescription() {
        return getDescription().hasDescription();
    }

    default boolean hasTag() {
        return getTags().hasTag();
    }

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getTaskType().equals(this.getTaskType())
                && other.getDescription().equals(this.getDescription()))
                && other.getPriority().equals(this.getPriority())
                && other.getRecur().equals(this.getRecur());
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskType().getDescription() + ": ");
        builder.append(getName());

        if (this.hasStartDate()) {
            builder.append("\nStarting on: ").append(getStartDate());
        }

        if (this.hasEndDate()) {
            builder.append("\nEnding on: ").append(getEndDate());
        }

        if (this.hasRecur()) {
            builder.append("\nRecurring: " + getRecur());
        }

        if (this.hasPriority()) {
            builder.append("\n").append("Priority: ").append(getPriority());
        }

        if (this.hasDescription()) {
            builder.append("\n").append("Description: ").append(getDescription());
        }

        if (this.hasTag()) {
            builder.append("\n").append("Tags: ");
            getTags().forEach(builder::append);
        }

        return builder.toString();
    }

}
